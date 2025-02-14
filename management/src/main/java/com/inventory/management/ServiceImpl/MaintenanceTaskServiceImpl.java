package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.CreateMaintenanceTaskRequest;
import com.inventory.management.Dtos.MaintenanceTaskDTO;
import com.inventory.management.Dtos.UpdateMaintenanceTaskRequest;
import com.inventory.management.Enums.MaintenanceTaskPriority;
import com.inventory.management.Enums.MaintenanceTaskStatus;
import com.inventory.management.Exceptions.FeatureDisabledException;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.MaintenanceTaskMapper;
import com.inventory.management.Model.MaintenanceTask;
import com.inventory.management.ReposiotryServices.MaintenanceTaskRepositoryService;
import com.inventory.management.service.MaintenanceTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaintenanceTaskServiceImpl implements MaintenanceTaskService {
    private final MaintenanceTaskRepositoryService repositoryService;
    private final MaintenanceTaskMapper mapper;
    @Value("${feature.maintenance.enabled:true}")
    private boolean maintenanceFeatureEnabled;

    private void checkFeatureEnabled() {
        if (!maintenanceFeatureEnabled) {
            log.warn("Maintenance feature is disabled");
            throw new FeatureDisabledException("Maintenance feature is disabled");
        }
    }


    @Override
    public Optional<MaintenanceTask> getMaintenanceTask(Long id) {
        checkFeatureEnabled();
        log.info("Fetching maintenance task with ID: {}", id);
        return repositoryService.getById(id);
    }

    @Override
    public Page<MaintenanceTask> getMaintenanceTasks(String status, Long vehicleId, Pageable pageable) {
        checkFeatureEnabled();
        log.info("Fetching tasks with status: {} and vehicleId: {}", status, vehicleId);

        return repositoryService.getTasksByStatusAndVehicle(status, vehicleId, pageable);
    }

    @Override
    public MaintenanceTaskDTO createMaintenanceTask(CreateMaintenanceTaskRequest request) {
        checkFeatureEnabled();
        log.info("Creating new maintenance task for vehicle ID: {}", request.getVehicleId());

        MaintenanceTask task = mapper.toEntity(request);
        task = repositoryService.save(task);
        log.info("Successfully created task with ID: {}", task.getId());

        return mapper.toDTO(task);
    }

    @Override
    public MaintenanceTaskDTO updateMaintenanceTask(Long id, UpdateMaintenanceTaskRequest dto) {
        checkFeatureEnabled();
        log.info("Updating maintenance task with ID: {}", id);

        MaintenanceTask task = repositoryService.getById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Task with ID " + id + " not found");
                });

        updateFieldsIfNotNull(dto, task);

        MaintenanceTask updatedTask = repositoryService.save(task);
        log.info("Task with ID {} successfully updated", id);
        return mapper.toDTO(updatedTask);
    }

    private void updateFieldsIfNotNull(UpdateMaintenanceTaskRequest dto, MaintenanceTask task) {
        Optional.ofNullable(dto.getDescription()).ifPresent(task::setDescription);
        Optional.ofNullable(dto.getVehicleId()).ifPresent(task::setVehicleId);
        Optional.ofNullable(dto.getScheduledDate()).ifPresent(task::setScheduledDate);
        Optional.ofNullable(dto.getStatus()).ifPresent(status -> task.setStatus(parseStatus(status)));
        Optional.ofNullable(dto.getPriority()).ifPresent(priority -> task.setPriority(parsePriority(priority)));
    }

    @Override
    public Page<MaintenanceTask> getTaskByVechileId(Long vehicleId, Pageable pageable) {
        log.info("Fetching tasks for vehicle ID: {}", vehicleId);

        return repositoryService.getTaskByVechileId(vehicleId, pageable);
    }


    @Override
    public void deleteMaintenanceTask(Long id) {
        repositoryService.deleteById(id);
    }

    private MaintenanceTaskStatus parseStatus(String status) {
        try {
            return MaintenanceTaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid status provided: {}", status);
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    private MaintenanceTaskPriority parsePriority(String priority) {
        try {
            return MaintenanceTaskPriority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid priority provided: {}", priority);
            throw new IllegalArgumentException("Invalid priority: " + priority);
        }
    }
}
