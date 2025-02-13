package com.inventory.management.Dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorBMeterReadingDTO {
    private String meterIdentifier;
    private Double energyUsage;
    private Long readingTimeEpoch;
    private Double current;
}