package com.inventory.management.Model;

import com.inventory.management.Enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "drone_flight_paths")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DroneFlightPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startLocation;
    private String endLocation;

    @Column(length = 2000)
    private String optimizedPath;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private Double totalDistance;
    private Double estimatedTime;
    private Double batteryConsumption;
}
