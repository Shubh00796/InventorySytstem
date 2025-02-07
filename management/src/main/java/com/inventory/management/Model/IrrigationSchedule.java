package com.inventory.management.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "irrigation_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrrigationSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fieldName;
    private Double waterAmount; // e.g., liters of water
    private LocalDateTime scheduleTime;
    private Boolean active;
}
