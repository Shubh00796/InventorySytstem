package com.inventory.management.Model;

import com.inventory.management.Enums.MaintenanceType;
import com.inventory.management.Enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeIdentifier;

    @Enumerated(EnumType.STRING)
    private MaintenanceType maintenanceType;

    private String description;

    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime scheduledDate;

    private LocalDateTime completedDate;
}