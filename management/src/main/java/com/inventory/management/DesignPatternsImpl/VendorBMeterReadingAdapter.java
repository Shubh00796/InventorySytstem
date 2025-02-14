package com.inventory.management.DesignPatternsImpl;

import com.inventory.management.Dtos.VendorBMeterReadingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.MeterReading;
import com.inventory.management.DesignPatternInterfaces.MeterReadingAdapter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class VendorBMeterReadingAdapter implements MeterReadingAdapter<VendorBMeterReadingDTO> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public MeterReading adapt(VendorBMeterReadingDTO dto) {
        if (dto.getReadingTimeEpoch() == null || dto.getReadingTimeEpoch() <= 0) {
            throw new ResourceNotFoundException("Valid readingTimeEpoch is required for Vendor B meter reading.");
        }
        LocalDateTime readingTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dto.getReadingTimeEpoch()), ZoneId.systemDefault());

        return MeterReading.builder()
                .meterId(dto.getMeterIdentifier())
                .consumption(dto.getEnergyUsage())
                .readingTimestamp(readingTime)
                .vendor("VENDOR_A")
                .build();
    }



}
