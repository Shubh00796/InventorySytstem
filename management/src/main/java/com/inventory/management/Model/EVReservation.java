package com.inventory.management.Model;

import com.inventory.management.Enums.EVReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ev_reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EVReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long stationId;
    @Column(nullable = false)
    private Long vehicleId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private LocalDateTime scheduledTime;
    @Column(nullable = false)
    private Integer durationMinutes;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EVReservationStatus status;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}