package com.inventory.management.Domain.DTOs;

import com.inventory.management.Enums.PackageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeliveryRequest {
    private String sender;
    private String recipient;
    private double packageWeight;
    private PackageType packageType;
    private boolean insurance;
    private boolean expeditedShipping;
    private boolean fragileHandling;
}