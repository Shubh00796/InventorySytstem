package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.ClaimDTO;
import com.inventory.management.Enums.ClaimStatus;
import com.inventory.management.Exceptions.ClaimNotFoundException;
import com.inventory.management.HelperClasses.PolicyNumberGenerator;
import com.inventory.management.Mapper.ClaimMapper;
import com.inventory.management.Model.Claim;
import com.inventory.management.ReposiotryServices.ClaimReposiotryService;
import com.inventory.management.events.ClaimSubmittedEvent;
import com.inventory.management.service.ClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {
    private final ApplicationEventPublisher eventPublisher;
    private final ClaimReposiotryService reposiotryService;
    private final ClaimMapper mapper;
    private final ConcurrentHashMap<Long, ReentrantLock> claimLocks = new ConcurrentHashMap<>();

    @Override
    public ClaimDTO createClaim(ClaimDTO claimDTO) {
        Claim entity = mapper.toEntity(claimDTO);
        entity.setPolicyNumber(PolicyNumberGenerator.generatePolicyNumber());
        entity.setClaimStatus(ClaimStatus.SUBMITTED);
        entity.setSubmittedAt(LocalDateTime.now());
        Claim savedClaim = reposiotryService.saveClaim(entity);
        eventPublisher.publishEvent(new ClaimSubmittedEvent(this, savedClaim.getId()));
        return mapper.toDto(savedClaim);
    }

    @Override
    public ClaimDTO getClaimById(Long claimId) {
        return reposiotryService.retriveClaimById(claimId)
                .map(mapper::toDto)
                .orElseThrow(() -> new ClaimNotFoundException("Id with given claim not found" + claimId));
    }

    @Override
    public List<ClaimDTO> getAllClaims() {
        return reposiotryService.retriveAllClaims()
                .parallelStream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ClaimDTO updateClaim(Long claimId, ClaimDTO claimDTO) {
        if (claimId == null) {
            throw new IllegalArgumentException("Claim id can not be null");
        }
        ReentrantLock reentrantLock = claimLocks.computeIfAbsent(claimId, id -> new ReentrantLock());
        boolean lockAcquired = false;
        try {
            if (reentrantLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                lockAcquired = true;
            }
            Claim claim = reposiotryService.retriveClaimByPolicyNumber(claimDTO.getPolicyNumber())
                    .orElseThrow(() -> new ClaimNotFoundException("Claim not found for ID: " + claimId));
            mapper.updateEntityFromDto(claimDTO, claim);
            claim.setClaimStatus(ClaimStatus.RESUBMITTED);

            Claim updatedSavedClaim = reposiotryService.saveClaim(claim);
            return mapper.toDto(updatedSavedClaim);
        } catch (Exception e) {
            throw new RuntimeException("Failed to acquire lock for claim update");
        } finally {
            if (lockAcquired) {
                reentrantLock.unlock();

                claimLocks.compute(claimId, (id, currentLock) -> currentLock != null && currentLock.hasQueuedThreads() ? currentLock : null);
            }
        }
    }


    @Override
    public void deleteClaim(Long claimId) {
        ReentrantLock reentrantLock = claimLocks.computeIfAbsent(claimId, key -> new ReentrantLock());
        boolean lockAcq = false;
        try {
            if (reentrantLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                lockAcq = true;
            }
            reposiotryService.deleteClaim(claimId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (lockAcq) {
                reentrantLock.unlock();


            }
        }

    }
}
