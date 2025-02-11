package com.inventory.management.ValidatorLogics;

import com.inventory.management.Repo.SensorReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorReadingValidator {
    private final SensorReadingRepository sensorReadingRepository;

    public void validateSensorIdNotPresent(String sensorId) {
        if (sensorReadingRepository.findBySensorId(sensorId).isPresent()) {
            throw new RuntimeException("Sensor ID " + sensorId + " is already present");
        }
    }
}