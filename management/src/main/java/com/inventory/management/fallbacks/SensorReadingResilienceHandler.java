package com.inventory.management.fallbacks;

import com.inventory.management.Dtos.SensorReadingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SensorReadingResilienceHandler {
    public List<SensorReadingDTO> fallbackGetAllSensorReadings(Exception e) {
        log.error("Fallback for getAllSensorReadings: {}", e.getMessage());
        return List.of();
    }

    public Optional<SensorReadingDTO> fallbackGetBySensorId(String sensorId, Exception e) {
        log.error("Fallback for getBySensorId: {}", e.getMessage());
        return Optional.empty();
    }

    public SensorReadingDTO fallbackCreateSensorReading(SensorReadingDTO sensorReadingDTO, Exception e) {
        log.error("Fallback for createSensorReading: {}", e.getMessage());
        throw new RuntimeException("Service unavailable, please try again later");
    }

    public SensorReadingDTO fallbackUpdateSensorReading(String sensorId, SensorReadingDTO sensorReadingDTO, Exception e) {
        log.error("Fallback for updateSensorReading: {}", e.getMessage());
        throw new RuntimeException("Service unavailable, please try again later");
    }

    public void fallbackDeleteSensorReading(Long id, Exception e) {
        log.error("Fallback for deleteSensorReading: {}", e.getMessage());
        throw new RuntimeException("Service unavailable, please try again later");
    }
}