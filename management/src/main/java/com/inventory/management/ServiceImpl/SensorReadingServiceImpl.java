package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.SensorReadingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.SensorReadingMapper;
import com.inventory.management.Model.SensorReading;
import com.inventory.management.Repo.SensorReadingRepository;
import com.inventory.management.ReposiotryServices.SensorReadingRepositoryService;
import com.inventory.management.ValidatorLogics.SensorReadingValidator;
import com.inventory.management.service.SensorReadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorReadingServiceImpl implements SensorReadingService {
    private final SensorReadingRepositoryService repositoryService;
    private final SensorReadingMapper sensorReadingMapper;
    private final SensorReadingValidator validator;

    @Override
    public List<SensorReadingDTO> getAllSensorReadings() {
        return repositoryService.getAll().stream()
                .map(sensorReadingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SensorReadingDTO> getBySensorId(String sensorId) {
        return repositoryService.findBySensorId(sensorId)
                .map(sensorReadingMapper::toDTO);
    }

    @Override
    public SensorReadingDTO createSensorReading(SensorReadingDTO sensorReadingDTO) {
        validator.validateSensorIdNotPresent(sensorReadingDTO.getSensorId());
        SensorReading savedSensor = repositoryService.save(sensorReadingMapper.toEntity(sensorReadingDTO));
        return sensorReadingMapper.toDTO(savedSensor);
    }

    @Override
    public SensorReadingDTO updateSensorReading(String sensorId, SensorReadingDTO sensorReadingDTO) {
        SensorReading updatedSensor = repositoryService.update(sensorId, sensorReadingDTO);
        return sensorReadingMapper.toDTO(updatedSensor);
    }

    @Override
    public void deleteSensorReading(Long id) {
        repositoryService.delete(id);
    }
}