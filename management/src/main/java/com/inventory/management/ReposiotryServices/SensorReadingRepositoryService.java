package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.SensorReadingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.SensorReading;
import com.inventory.management.Repo.SensorReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorReadingRepositoryService {

    private final SensorReadingRepository sensorReadingRepository;

    public List<SensorReading> getAll() {
        return sensorReadingRepository.findAll();
    }
    public Optional<SensorReading> findBySensorId(String sensorId) {
        return sensorReadingRepository.findBySensorId(sensorId);
    }
    public SensorReading save(SensorReading sensorReading) {
        sensorReading.setTimestamp(LocalDateTime.now());
        return sensorReadingRepository.save(sensorReading);
    }
    public SensorReading update(String sensorId, SensorReadingDTO sensorReadingDTO) {
        SensorReading sensorReading = findBySensorId(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor with ID " + sensorId + " not found"));

        Optional.ofNullable(sensorReadingDTO.getValue()).ifPresent(sensorReading::setValue);

        return sensorReadingRepository.save(sensorReading);
    }
    public void delete(Long id) {
        SensorReading existingReading = sensorReadingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor reading not found with id: " + id));
        sensorReadingRepository.delete(existingReading);
    }
}
