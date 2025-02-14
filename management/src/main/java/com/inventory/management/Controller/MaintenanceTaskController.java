package com.inventory.management.Controller;

import com.inventory.management.Dtos.CreateMaintenanceTaskRequest;
import com.inventory.management.Dtos.MaintenanceTaskDTO;
import com.inventory.management.Dtos.UpdateMaintenanceTaskRequest;
import com.inventory.management.Model.MaintenanceTask;
import com.inventory.management.service.MaintenanceTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/maintenance-tasks")
@RequiredArgsConstructor
public class MaintenanceTaskController {
    private final MaintenanceTaskService service;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MaintenanceTask>> getTask(@PathVariable Long id) {
        Optional<MaintenanceTask> task = service.getMaintenanceTask(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<MaintenanceTaskDTO> createTask(@RequestBody CreateMaintenanceTaskRequest request) {
        return ResponseEntity.ok(service.createMaintenanceTask(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceTaskDTO> updateTask(@PathVariable Long id, @RequestBody UpdateMaintenanceTaskRequest request) {
        return ResponseEntity.ok(service.updateMaintenanceTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteMaintenanceTask(id);
        return ResponseEntity.noContent().build();
    }
}
