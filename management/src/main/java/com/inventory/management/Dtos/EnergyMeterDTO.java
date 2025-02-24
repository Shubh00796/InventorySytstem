package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyMeterDTO {
    private Long id;
    private String meterId;
    private String location;
    private double threshold;
    private double currentConsumption;
}