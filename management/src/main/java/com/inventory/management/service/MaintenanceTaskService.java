package com.inventory.management.service;

import com.inventory.management.Dtos.CreateMaintenanceTaskRequest;
import com.inventory.management.Dtos.MaintenanceTaskDTO;
import com.inventory.management.Dtos.UpdateMaintenanceTaskRequest;
import com.inventory.management.Model.MaintenanceTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MaintenanceTaskService {
    Optional<MaintenanceTask> getMaintenanceTask(Long id);
    Page<MaintenanceTask> getMaintenanceTasks(String status, Long vehicleId, Pageable pageable);
    MaintenanceTaskDTO createMaintenanceTask(CreateMaintenanceTaskRequest request);
    MaintenanceTaskDTO updateMaintenanceTask(Long id, UpdateMaintenanceTaskRequest request);
    Page<MaintenanceTask> getTaskByVechileId(Long vehicleId, Pageable pageable);
    void deleteMaintenanceTask(Long id);
}