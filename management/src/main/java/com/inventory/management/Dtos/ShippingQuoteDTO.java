package com.inventory.management.Dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingQuoteDTO {
    private Long id;
    private Double weight;
    private Double distance;
    private String shippingMethod;
    private String carrier;
    private Double cost;
    private LocalDateTime createdAt;
}