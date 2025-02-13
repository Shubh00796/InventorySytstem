package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterReadingDTO {
    private Long id;
    private String meterId;
    private Double consumption;
    private LocalDateTime readingTimestamp;
    private String vendor;
}