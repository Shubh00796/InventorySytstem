package com.inventory.management.Dtos;

import com.inventory.management.Enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DroneFlightPathDTO {
    private Long id;
    private String startLocation;
    private String endLocation;
    private String optimizedPath;
    private FlightStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private Double totalDistance;
    private Double estimatedTime;
    private Double batteryConsumption;
}
