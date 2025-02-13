package com.inventory.management.Dtos;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GarminActivityDTO {
    private String deviceIdentifier;
    private Integer stepsTaken;
    private Double heartRate;
    private Long recordedTime;
    private String firmwareVersion;
}