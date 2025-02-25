package com.inventory.management.Controller;

import com.inventory.management.Dtos.MaintenanceRequestDto;
import com.inventory.management.service.MaintenanceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenance-requests")
@RequiredArgsConstructor
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;

    @PostMapping
    public ResponseEntity<MaintenanceRequestDto> createMaintenanceRequest(@Valid @RequestBody MaintenanceRequestDto dto) {
        MaintenanceRequestDto createdRequest = maintenanceRequestService.createMaintenanceRequest(dto);
        return ResponseEntity.ok(createdRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRequestDto> getMaintenanceRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceRequestService.getMaintenanceRequestById(id));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRequestDto>> getAllMaintenanceRequests() {
        return ResponseEntity.ok(maintenanceRequestService.getAllMaintenanceRequests());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MaintenanceRequestDto> updateMaintenanceRequestStatus(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceRequestDto dto) {
        return ResponseEntity.ok(maintenanceRequestService.updateMaintenanceRequestStatus(id, dto));
    }
}
