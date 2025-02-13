package com.inventory.management.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private Integer steps;

    private Double heartRate;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    @Column(nullable = false)
    private String source;
}