package com.inventory.management.service;


import com.inventory.management.Dtos.MaintenanceRequestDto;

import java.util.List;

public interface MaintenanceRequestService {
    MaintenanceRequestDto createMaintenanceRequest(MaintenanceRequestDto maintenanceRequestDto);

    MaintenanceRequestDto getMaintenanceRequestById(Long id);

    List<MaintenanceRequestDto> getAllMaintenanceRequests();

    MaintenanceRequestDto updateMaintenanceRequestStatus(Long id, MaintenanceRequestDto maintenanceRequestDto);
}