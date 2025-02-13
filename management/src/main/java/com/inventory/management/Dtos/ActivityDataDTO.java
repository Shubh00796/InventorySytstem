package com.inventory.management.Dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDataDTO {
    private Long id;
    private String deviceId;
    private Integer steps;
    private Double heartRate;
    private LocalDateTime recordedAt;
    private String source;
}