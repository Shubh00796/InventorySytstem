package com.inventory.management.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "meter_readings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String meterId;

    @Column(nullable = false)
    private Double consumption;

    @Column(nullable = false)
    private LocalDateTime readingTimestamp;

    @Column(nullable = false)
    private String vendor;
}