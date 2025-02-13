package com.inventory.management.Dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorAMeterReadingDTO {
    private String meterId;
    private Double consumption;
    private String timestamp;
    private Double voltage;
}