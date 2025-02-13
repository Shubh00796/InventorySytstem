package com.inventory.management.VendorAdapators;

import com.inventory.management.Dtos.GarminActivityDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.ActivityData;
import com.inventory.management.VendorInterfaces.ActivityDataAdapter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class GarminActivityDataAdapter implements ActivityDataAdapter<GarminActivityDTO> {

    @Override
    public ActivityData adapt(GarminActivityDTO dto) {
        LocalDateTime recordedAt = parseDateTime(dto.getRecordedTime());

        return ActivityData.builder()
                .deviceId(dto.getDeviceIdentifier())
                .steps(dto.getStepsTaken())
                .heartRate(dto.getHeartRate())
                .recordedAt(recordedAt)
                .source("GARMIN")
                .build();
    }

    private LocalDateTime parseDateTime(Long epochMillis) {
        if (epochMillis == null || epochMillis <= 0) {
            throw new ResourceNotFoundException("Valid recordedTime is required.");
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault());
    }
}