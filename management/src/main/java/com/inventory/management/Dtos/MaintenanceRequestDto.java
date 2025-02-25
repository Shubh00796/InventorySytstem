package com.inventory.management.Dtos;

import com.inventory.management.Enums.MaintenanceType;
import com.inventory.management.Enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRequestDto {
    private Long id;
    private String storeIdentifier;
    private MaintenanceType maintenanceType;
    private String description;
    private LocalDateTime requestDate;
    private RequestStatus status;
    private LocalDateTime scheduledDate;
    private LocalDateTime completedDate;
}
