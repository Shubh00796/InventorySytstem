package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.IrrigationScheduleDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.IrrigationScheduleMapper;
import com.inventory.management.Model.IrrigationSchedule;
import com.inventory.management.Repo.IrrigationScheduleRepository;
import com.inventory.management.service.IrrigationScheduleService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IrrigationScheduleServiceImpl implements IrrigationScheduleService {

    private final IrrigationScheduleRepository irrigationScheduleRepository;
    private final IrrigationScheduleMapper irrigationScheduleMapper;

    private static final String CIRCUIT_BREAKER_NAME = "irrigationService";
    private static final String RATE_LIMITER_NAME = "irrigationRateLimiter";

    @Override
    @RateLimiter(name = RATE_LIMITER_NAME, fallbackMethod = "getAllSchedulesFallback")
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAllSchedulesFallback")
    public List<IrrigationScheduleDTO> getAllSchedules() {
        log.info("Fetching all irrigation schedules");
        return irrigationScheduleRepository.findAll().stream()
                .map(irrigationScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "inventoryItems", key = "#id", unless = "#result == null")
    @RateLimiter(name = RATE_LIMITER_NAME, fallbackMethod = "getScheduleByIdFallback")
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getScheduleByIdFallback")
    public List<IrrigationScheduleDTO> getScheduleById(Long id) {
        log.info("Fetching irrigation schedule by ID: {}", id);
        return irrigationScheduleRepository.findById(id)
                .map(schedule -> List.of(irrigationScheduleMapper.toDTO(schedule)))
                .orElse(Collections.emptyList());
    }

    @Override
    @RateLimiter(name = RATE_LIMITER_NAME, fallbackMethod = "createScheduleFallback")
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "createScheduleFallback")
    public IrrigationScheduleDTO createSchedule(IrrigationScheduleDTO scheduleDTO) {
        log.info("Creating new irrigation schedule");
        IrrigationSchedule entity = irrigationScheduleMapper.toEntity(scheduleDTO);
        IrrigationSchedule savedEntity = irrigationScheduleRepository.save(entity);
        return irrigationScheduleMapper.toDTO(savedEntity);
    }

    @Override
    @CacheEvict(value = "inventoryItems", key = "#id")
    @RateLimiter(name = RATE_LIMITER_NAME, fallbackMethod = "updateScheduleFallback")
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "updateScheduleFallback")
    public IrrigationScheduleDTO updateSchedule(Long id, IrrigationScheduleDTO scheduleDTO) {
        log.info("Updating irrigation schedule with ID: {}", id);
        IrrigationSchedule existingSchedule = irrigationScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigation Schedule not found with id: " + id));
        updateScheduleFields(scheduleDTO, existingSchedule);
        IrrigationSchedule updatedSchedule = irrigationScheduleRepository.save(existingSchedule);
        return irrigationScheduleMapper.toDTO(updatedSchedule);
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteScheduleFallback")

    public void deleteSchedule(Long id) {
        log.info("Deleting irrigation schedule with ID: {}", id);
        if (!irrigationScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Irrigation Schedule not found with id: " + id);
        }
        irrigationScheduleRepository.deleteById(id);
    }

    private List<IrrigationScheduleDTO> getAllSchedulesFallback(Throwable e) {
        log.error("Fallback: Unable to fetch all schedules. Error: {}", e.getMessage());
        return Collections.emptyList();
    }

    private List<IrrigationScheduleDTO> getScheduleByIdFallback(Long id, Throwable e) {
        log.error("Fallback: Unable to fetch schedule for id {}. Error: {}", id, e.getMessage());
        return Collections.emptyList();
    }

    private IrrigationScheduleDTO createScheduleFallback(IrrigationScheduleDTO scheduleDTO, Throwable e) {
        log.error("Fallback: Unable to create schedule. Error: {}", e.getMessage());
        return new IrrigationScheduleDTO();
    }

    private IrrigationScheduleDTO updateScheduleFallback(Long id, IrrigationScheduleDTO scheduleDTO, Throwable e) {
        log.error("Fallback: Unable to update schedule for id {}. Error: {}", id, e.getMessage());
        return new IrrigationScheduleDTO();
    }

    private void deleteScheduleFallback(Long id, Throwable e) {
        log.error("Fallback: Unable to delete schedule for id {}. Error: {}", id, e.getMessage());
    }

//    private static void updateScheduleFields(IrrigationScheduleDTO scheduleDTO, IrrigationSchedule existingSchedule) {
//        if (scheduleDTO.getFieldName() != null) {
//            existingSchedule.setFieldName(scheduleDTO.getFieldName());
//        }
//        if (scheduleDTO.getWaterAmount() != null) {
//            existingSchedule.setWaterAmount(scheduleDTO.getWaterAmount());
//        }
//        if (scheduleDTO.getScheduleTime() != null) {
//            existingSchedule.setScheduleTime(scheduleDTO.getScheduleTime());
//        }
//        if (scheduleDTO.getActive() != null) {
//            existingSchedule.setActive(scheduleDTO.getActive());
//        }
//    }

    private void updateScheduleFields(IrrigationScheduleDTO scheduleDTO, IrrigationSchedule existingSchedule) {
        List<Consumer<IrrigationSchedule>> updates = List.of(
                s -> {
                    if (scheduleDTO.getFieldName() != null) s.setFieldName(scheduleDTO.getFieldName());
                },
                s -> {
                    if (scheduleDTO.getWaterAmount() != null) s.setWaterAmount(scheduleDTO.getWaterAmount());
                },
                s -> {
                    if (scheduleDTO.getScheduleTime() != null) s.setScheduleTime(scheduleDTO.getScheduleTime());
                },
                s -> {
                    if (scheduleDTO.getActive() != null) s.setActive(scheduleDTO.getActive());
                }
        );

        updates.forEach(update -> update.accept(existingSchedule));
    }

}