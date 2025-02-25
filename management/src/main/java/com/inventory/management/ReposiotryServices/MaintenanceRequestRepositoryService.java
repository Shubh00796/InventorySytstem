package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.MaintenanceRequestNotFoundException;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.MaintenanceRequest;
import com.inventory.management.Repo.MaintenanceRequestRepository;
import com.inventory.management.Repo.MaintenanceTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestRepositoryService {
    private final MaintenanceRequestRepository repository;

    public List<MaintenanceRequest> retriveAllRequests() {
        return repository.findAll();
    }

    public MaintenanceRequest retriveRequestById(Long requestId) {
        return getMaintenanceRequest(requestId);
    }

    public MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest maintenanceRequest) {
        return repository.save(maintenanceRequest);
    }

    private MaintenanceRequest getMaintenanceRequest(Long requestId) {
        return repository.findById(requestId)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException("THE ID WITH THE GIVEN RESOURCE NOT FOUND" + requestId));
    }

}
