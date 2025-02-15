package com.inventory.management.Dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EVReservationDTO {
    private Long id;
    private Long stationId;
    private Long vehicleId;
    private Long userId;
    @Future
    private LocalDateTime scheduledTime;
    @Min(15)
    private Integer durationMinutes;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}