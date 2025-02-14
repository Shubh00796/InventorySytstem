package com.inventory.management.ReposiotryServices;

import com.inventory.management.Enums.MaintenanceTaskStatus;
import com.inventory.management.Model.MaintenanceTask;
import com.inventory.management.Repo.MaintenanceTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceTaskRepositoryService {
    private final MaintenanceTaskRepository repository;

    public Optional<MaintenanceTask> getById(Long id) {
        return repository.findById(id);
    }

    public Page<MaintenanceTask> getTasksByStatusAndVehicle(String status, Long vehicleId, Pageable pageable) {
        return repository.findByStatusAndVehicleId(
                MaintenanceTaskStatus.valueOf(status.toUpperCase()), vehicleId, pageable);
    }

    public MaintenanceTask save(MaintenanceTask maintenanceTask) {
        return repository.save(maintenanceTask);
    }

    public Page<MaintenanceTask> getTaskByVechileId(Long vehicleId, Pageable pageable) {
        return repository.findByVehicleId(vehicleId, pageable);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Task not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
