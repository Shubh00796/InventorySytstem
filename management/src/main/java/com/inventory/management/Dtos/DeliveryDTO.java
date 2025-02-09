package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDTO {
    private Long id;
    private String trackingNumber;
    private String courier;
    private String status;
    private String pickupAddress;
    private String deliveryAddress;
    private LocalDateTime estimatedDeliveryTime;
}
