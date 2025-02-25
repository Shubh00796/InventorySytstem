package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.DroneFlightPathDTO;
import com.inventory.management.Enums.FlightStatus;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.DronePathMapper;
import com.inventory.management.Model.DroneFlightPath;
import com.inventory.management.ReposiotryServices.FlighPathReposiotrService;
import com.inventory.management.events.FlightPathOptimizationEvent;
import com.inventory.management.service.DroneFlightPathService;
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
public class DroneFlightPathSeriveImpl implements DroneFlightPathService {
    private final ApplicationEventPublisher eventPublisher;
    private final FlighPathReposiotrService reposiotrService;
    private final DronePathMapper mapper;
    private final ConcurrentHashMap<Long, ReentrantLock> flightLocks = new ConcurrentHashMap<>();

    @Override
    public DroneFlightPathDTO createFlightPath(DroneFlightPathDTO flightPathDTO) {
        DroneFlightPath entity = mapper.toEntity(flightPathDTO);
        entity.setStatus(FlightStatus.SUBMITTED);
        entity.setSubmittedAt(LocalDateTime.now());
        DroneFlightPath droneFlightPath = reposiotrService.saveDroneFlightPath(entity);
        eventPublisher.publishEvent(new FlightPathOptimizationEvent(this, droneFlightPath.getId()));

        return mapper.toDto(droneFlightPath);
    }

    @Override
    public DroneFlightPathDTO getFlightPathById(Long flightId) {
        DroneFlightPath droneFlightPath = getDroneFlightPathById(flightId);
        return mapper.toDto(droneFlightPath);
    }

    private DroneFlightPath getDroneFlightPathById(Long flightId) {
        DroneFlightPath droneFlightPath = reposiotrService.retriveFlighById(flightId);
        return droneFlightPath;
    }

    @Override
    public List<DroneFlightPathDTO> getAllFlightPaths() {
        return reposiotrService.retriveAllFlightPaths()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public DroneFlightPathDTO updateFlightPath(Long flightId, DroneFlightPathDTO flightPathDTO) {
        if (flightId == null) {
            throw new IllegalArgumentException("Flight id can not be Null");

        }
        ReentrantLock reentrantLock = getReentrantLock(flightId);
        boolean lockAcquried = false;
        try {
            if (reentrantLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                lockAcquried = true;
            }
            DroneFlightPath existingFlightPath = reposiotrService.retriveFlighById(flightId);
            mapper.updateEntityFromDto(flightPathDTO, existingFlightPath);
            DroneFlightPath updatedDroneFlightPath = reposiotrService.saveDroneFlightPath(existingFlightPath);
            return mapper.toDto(updatedDroneFlightPath);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        } finally {
            if (lockAcquried) {
                reentrantLock.unlock();
            }
        }
    }

    private ReentrantLock getReentrantLock(Long flightId) {
        ReentrantLock reentrantLock = flightLocks.computeIfAbsent(flightId, id -> new ReentrantLock());
        return reentrantLock;
    }

    @Override
    public void deleteFlightPath(Long flightId) {
        ReentrantLock reentrantLock = getReentrantLock(flightId);
        boolean lockAcquired = false;
        try {
            if (reentrantLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                lockAcquired = true;
            }
            reposiotrService.deletedroneFlightPath(flightId);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        } finally {
            if (lockAcquired) {
                reentrantLock.unlock();
            }
        }


    }
}
