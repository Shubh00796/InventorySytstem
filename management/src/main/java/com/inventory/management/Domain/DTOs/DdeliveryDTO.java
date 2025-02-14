package com.inventory.management.Domain.DTOs;

import com.inventory.management.Enums.PackageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DdeliveryDTO {
    private UUID id;
    private String sender;
    private String recipient;
    private double packageWeight;
    private PackageType packageType;
    private boolean insurance;
    private boolean expeditedShipping;
    private boolean fragileHandling;
    private double shippingCost;
}