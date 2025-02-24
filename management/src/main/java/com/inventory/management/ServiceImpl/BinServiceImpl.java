package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.BinDTO;
import com.inventory.management.HelperClasses.BinHelpers;
import com.inventory.management.Mapper.BinMapper;
import com.inventory.management.Model.Bin;
import com.inventory.management.ReposiotryServices.BinRepositryService;
import com.inventory.management.events.BinFullEvent;
import com.inventory.management.service.BinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BinServiceImpl implements BinService {
    private final BinRepositryService repositryService;
    private final BinMapper mapper;
    private final BinHelpers helper;
    private final ApplicationEventPublisher eventPublisher;
    private final ConcurrentHashMap<Long, ReentrantLock> binLocks = new ConcurrentHashMap<>();

    @Override
    public BinDTO createBin(BinDTO binDTO) {
        if (binDTO.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        Bin entity = mapper.toEntity(binDTO);
        log.info("Bin saved with id: {}", entity.getId());

        Bin savedBin = repositryService.saveBin(entity);
        log.info("Bin saved with id: {}", savedBin.getId());


        if (helper.isFull(savedBin)) {
            log.info("Bin {} is full. Publishing BinFullEvent.", savedBin.getId());
            eventPublisher.publishEvent(new BinFullEvent(this, savedBin));
        }


        return mapper.toDto(savedBin);
    }

    @Override
    public BinDTO updateBin(BinDTO binDTO) throws InterruptedException {
        Long binId = binDTO.getId();
        if (binId == null) {
            throw new IllegalArgumentException("Bin ID cannot be null");
        }
        ReentrantLock reentrantLock = binLocks.computeIfAbsent(binId, id -> new ReentrantLock());

        boolean lockAcquired = false;

        try {
            if (reentrantLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                lockAcquired = true;
            }

            Bin existingBin = repositryService.findBinById(binId);
            Optional.ofNullable(binDTO.getCapacity()).ifPresent(existingBin::setCapacity);

            return mapper.toDto(repositryService.saveBin(existingBin));

        } finally {
            if (lockAcquired) {
                reentrantLock.unlock();

            }
            binLocks.compute(binId, (id, currentLock) -> (currentLock != null && currentLock.hasQueuedThreads()) ? currentLock : null);

        }


    }

    @Override
    @Cacheable(value = "bins", key = "#id")
    public Optional<BinDTO> getBinById(Long id) {
        Bin bin = repositryService.findBinById(id);
        return Optional.of(mapper.toDto(bin));
    }

    @Override
    @Cacheable(value = "allBins", key = "#page + '-' + #size")
    public List<BinDTO> getAllBins(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repositryService.findAllBins()
                .parallelStream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @CacheEvict(value = "bins", key = "#id")
    public void deleteBin(Long id) throws InterruptedException {
        ReentrantLock reentrantLock = binLocks.computeIfAbsent(id, key -> new ReentrantLock());

        boolean lockAcquired = false;

        try {
            if (reentrantLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                lockAcquired = true;
            }
            repositryService.deleteBin(id);
            log.info("Bin {} deleted and removed from cache.", id);

        } finally {
            if (lockAcquired) {
                reentrantLock.unlock();
                binLocks.compute(id, (key, currentLock) -> currentLock.hasQueuedThreads() ? currentLock : null);
            }

        }

    }
}
