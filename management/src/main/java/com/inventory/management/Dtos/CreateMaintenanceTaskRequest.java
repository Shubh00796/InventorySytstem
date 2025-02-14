package com.inventory.management.Dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMaintenanceTaskRequest {
    @NotNull
    private Long vehicleId;
    @NotNull
    @Future
    private LocalDateTime scheduledDate;
    @NotNull
    @Size(min = 5, max = 255)
    private String description;
    @NotNull
    private String status;
    @NotNull
    private String priority;
}