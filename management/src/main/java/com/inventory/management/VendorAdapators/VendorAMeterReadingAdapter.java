package com.inventory.management.VendorAdapators;

import com.inventory.management.Dtos.VendorAMeterReadingDTO;
import com.inventory.management.Model.MeterReading;
import com.inventory.management.VendorInterfaces.MeterReadingAdapter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class VendorAMeterReadingAdapter implements MeterReadingAdapter<VendorAMeterReadingDTO> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public MeterReading adapt(VendorAMeterReadingDTO dto) {
        LocalDateTime readingTime = parseTimestamp(dto.getTimestamp());

        return MeterReading.builder()
                .meterId(dto.getMeterId())
                .consumption(dto.getConsumption())
                .readingTimestamp(readingTime)
                .vendor("VENDOR_A")
                .build();
    }

    private LocalDateTime parseTimestamp(String timestamp) {
        if (timestamp == null || timestamp.isBlank()) {
            throw new IllegalArgumentException("Timestamp is required for Vendor A meter reading.");
        }
        try {
            return LocalDateTime.parse(timestamp, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format. Expected: yyyy-MM-dd HH:mm:ss");
        }
    }
}
