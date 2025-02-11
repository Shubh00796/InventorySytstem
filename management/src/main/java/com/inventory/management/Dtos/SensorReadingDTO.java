package com.inventory.management.Dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReadingDTO {
    private Long id;
    private String sensorId;
    private Double value;
    private LocalDateTime timestamp;
}