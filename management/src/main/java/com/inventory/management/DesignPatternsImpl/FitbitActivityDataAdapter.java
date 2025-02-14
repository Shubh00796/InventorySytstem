package com.inventory.management.DesignPatternsImpl;

import com.inventory.management.Dtos.FitbitActivityDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.ActivityData;
import com.inventory.management.DesignPatternInterfaces.ActivityDataAdapter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FitbitActivityDataAdapter implements ActivityDataAdapter<FitbitActivityDTO> {


    @Override
    public ActivityData adapt(FitbitActivityDTO dto) {
        if (dto.getTimeRecorded() == null || dto.getTimeRecorded().isBlank()) {
            throw new RuntimeException("Time recorded is required.");

        }
        LocalDateTime recordedAt = parseDateTime(dto.getTimeRecorded());
        Double heartRate = parseHeartRate(dto.getAvgHeartRate());

        return ActivityData.builder()
                .deviceId(dto.getDeviceId())
                .steps(dto.getStepCount())
                .heartRate(heartRate)
                .recordedAt(recordedAt)
                .source("FITBIT")
                .build();


    }

    private LocalDateTime parseDateTime(String time) {
        try {
            return LocalDateTime.parse(time);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Invalid time format. Expected ISO-8601.");
        }
    }

    private Double parseHeartRate(String heartRateStr) {
        if (heartRateStr == null || heartRateStr.isBlank()) return null;
        try {
            return Double.parseDouble(heartRateStr.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            return null;
        }
    }
}
