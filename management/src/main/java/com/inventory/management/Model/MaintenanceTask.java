package com.inventory.management.Model;

import com.inventory.management.Enums.MaintenanceTaskPriority;
import com.inventory.management.Enums.MaintenanceTaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;
    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceTaskStatus status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceTaskPriority priority;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}