package com.inventory.management.service;

import com.inventory.management.Dtos.SensorReadingDTO;

import java.util.List;
import java.util.Optional;

public interface SensorReadingService {
    List<SensorReadingDTO> getAllSensorReadings();

    Optional<SensorReadingDTO> getBySensorId(String sensorId);

    SensorReadingDTO createSensorReading(SensorReadingDTO sensorReadingDTO);

    SensorReadingDTO updateSensorReading(String sensorId, SensorReadingDTO sensorReadingDTO);

    void deleteSensorReading(Long id);
}