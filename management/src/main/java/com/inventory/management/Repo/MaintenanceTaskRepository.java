package com.inventory.management.Repo;

import com.inventory.management.Enums.MaintenanceTaskStatus;
import com.inventory.management.Model.MaintenanceTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Long> {
    Page<MaintenanceTask> findByStatus(MaintenanceTaskStatus status, Pageable pageable);
    Page<MaintenanceTask> findByVehicleId(Long vehicleId, Pageable pageable);
    Page<MaintenanceTask> findByStatusAndVehicleId(MaintenanceTaskStatus status, Long vehicleId, Pageable pageable);
}