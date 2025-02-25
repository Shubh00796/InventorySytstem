package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.MaintenanceRequestDto;
import com.inventory.management.Enums.RequestStatus;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.MaintainceMapper;
import com.inventory.management.Mapper.MaintenanceTaskMapper;
import com.inventory.management.Model.MaintenanceRequest;
import com.inventory.management.ReposiotryServices.MaintenanceRequestRepositoryService;
import com.inventory.management.events.FlightPathOptimizationEvent;
import com.inventory.management.service.MaintenanceRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService {
    private final ApplicationEventPublisher eventPublisher;
    private final MaintenanceRequestRepositoryService repositoryService;
    private final MaintainceMapper mapper;
    private final ConcurrentHashMap<Long, ReentrantLock> maintenanceLocks = new ConcurrentHashMap<>();

    @Override
    public MaintenanceRequestDto createMaintenanceRequest(MaintenanceRequestDto dto) {
        MaintenanceRequest entity = mapper.toEntity(dto);
        entity.setStatus(RequestStatus.PENDING);
        entity.setRequestDate(LocalDateTime.now());
        MaintenanceRequest savedRequest = repositoryService.saveMaintenanceRequest(entity);
        eventPublisher.publishEvent(new FlightPathOptimizationEvent(this, savedRequest.getId()));

        return mapper.toDto(savedRequest);
    }

    @Override
    public MaintenanceRequestDto getMaintenanceRequestById(Long requestId) {
        MaintenanceRequest maintenanceRequest = repositoryService.retriveRequestById(requestId);
        return mapper.toDto(maintenanceRequest);
    }

    @Override
    public List<MaintenanceRequestDto> getAllMaintenanceRequests() {
        return repositoryService.retriveAllRequests().parallelStream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public MaintenanceRequestDto updateMaintenanceRequestStatus(Long requestId, MaintenanceRequestDto maintenanceRequestDto) {
        if (requestId == null) {
            throw new IllegalArgumentException("The request that you are sending can not be null" + requestId);

        }
        ReentrantLock reentrantLock = maintenanceLocks.computeIfAbsent(requestId, id -> new ReentrantLock());
        boolean lockAcqured = false;
        try {
            if (reentrantLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                lockAcqured = true;
            }
            MaintenanceRequest existingRequest = repositoryService.retriveRequestById(requestId);

            mapper.updateEntityFromDto(maintenanceRequestDto, existingRequest);

            return mapper.toDto(repositoryService.saveMaintenanceRequest(existingRequest));

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        } finally {
            if (lockAcqured) {
                reentrantLock.unlock();

                maintenanceLocks.compute(requestId, (key, currentLock) -> currentLock != null && currentLock.hasQueuedThreads() ? currentLock : null);
            }
        }
    }
}
