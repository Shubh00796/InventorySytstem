package com.inventory.management.ReposiotryServices;

import com.inventory.management.Enums.MaintenanceTaskStatus;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.MaintenanceTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
    private final MaintenanceTaskRepositoryService repositoryService;

    public void updateStatus(Long id, String newStatus) {
        MaintenanceTask task = repositoryService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));

        String sanitizedStatus = newStatus.trim().toUpperCase();

        if (!isValidStatus(sanitizedStatus)) {
            throw new IllegalArgumentException("Invalid status: " + newStatus);
        }



        task.setStatus(MaintenanceTaskStatus.valueOf(sanitizedStatus));
        repositoryService.save(task);
    }

    private boolean isValidStatus(String status) {
        for (MaintenanceTaskStatus s : MaintenanceTaskStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

}