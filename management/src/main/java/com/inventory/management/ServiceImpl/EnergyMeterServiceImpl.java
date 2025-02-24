package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.EnergyMeterDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.EnergyMeterMapper;
import com.inventory.management.Model.EnergyMeter;
import com.inventory.management.ReposiotryServices.EnergyMeterRepositoryService;
import com.inventory.management.events.EnergyThresholdExceededEvent;
import com.inventory.management.service.EnergyMeterService;
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
public class EnergyMeterServiceImpl implements EnergyMeterService {

    private final EnergyMeterRepositoryService repositoryService;
    private final ApplicationEventPublisher eventPublisher;
    private final EnergyMeterMapper mapper;
    private final ConcurrentHashMap<Long, ReentrantLock> meterLocks = new ConcurrentHashMap<>();

    @Override
    public EnergyMeterDTO createEnergyMeter(EnergyMeterDTO energyMeterDTO) {
        if (energyMeterDTO.getThreshold() <= 0) {
            throw new IllegalArgumentException("Threshold must be greater than zero.");
        }

        EnergyMeter entity = mapper.toEntity(energyMeterDTO);
        EnergyMeter savedMeter = repositoryService.saveEnergyMeter(entity);

        log.info("Energy Meter created with ID: {}", savedMeter.getId());
        return mapper.toDto(savedMeter);
    }

    @Override
    public EnergyMeterDTO updateEnergyMeter(Long id, EnergyMeterDTO energyMeterDTO) {
        EnergyMeter existingMeter = repositoryService.retrieveEnergyMeterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Energy Meter not found with ID: " + id));

        Optional.ofNullable(energyMeterDTO.getLocation()).ifPresent(existingMeter::setLocation);
        Optional.ofNullable(energyMeterDTO.getThreshold()).ifPresent(existingMeter::setThreshold);

        EnergyMeter updatedMeter = repositoryService.saveEnergyMeter(existingMeter);
        return mapper.toDto(updatedMeter);
    }

    @Override
    public EnergyMeterDTO recordEnergyConsumption(Long id, double newConsumption) {
        EnergyMeter meter = repositoryService.retrieveEnergyMeterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Energy Meter not found with ID: " + id));

        meter.setCurrentConsumption(newConsumption);
        EnergyMeter updatedMeter = repositoryService.saveEnergyMeter(meter);

        checkAndPublishThresholdExceededEvent(updatedMeter);
        return mapper.toDto(updatedMeter);
    }

    @Override
    public EnergyMeterDTO getEnergyMeterById(String meterId) {
        return null;
    }

    @Cacheable(value = "meter", key = "#id")
    @Override
    public EnergyMeterDTO getEnergyMeterById(Long id) {
        return repositoryService.retrieveEnergyMeterById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Energy Meter not found with ID: " + id));
    }

    @Override
    @Cacheable(value = "allMeters", key = "#page + '-' + #size")
    public List<EnergyMeterDTO> getAllEnergyMeters(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repositoryService.retrieveAllEnergies(pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }



    @CacheEvict(value = "meter", key = "#id")
    @Override
    public void deleteEnergyMeter(Long id) {
        ReentrantLock lock = meterLocks.computeIfAbsent(id, key -> new ReentrantLock());
        boolean lockAcquired = false;

        try {
            if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                lockAcquired = true;
                repositoryService.deleteEnergyMeter(id);
                log.info("Energy Meter {} deleted successfully.", id);
            } else {
                throw new RuntimeException("Could not acquire lock to delete Energy Meter: " + id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while deleting Energy Meter: " + id, e);
        } finally {
            if (lockAcquired) {
                lock.unlock();
                meterLocks.computeIfPresent(id, (key, currentLock) ->
                        currentLock.hasQueuedThreads() ? currentLock : null
                );
            }
        }
    }

    private void checkAndPublishThresholdExceededEvent(EnergyMeter meter) {
        if (meter.getCurrentConsumption() > meter.getThreshold()) {
            log.info("Energy Meter {} exceeded threshold. Publishing event.", meter.getMeterId());
            eventPublisher.publishEvent(new EnergyThresholdExceededEvent(meter));
        }
    }
}
