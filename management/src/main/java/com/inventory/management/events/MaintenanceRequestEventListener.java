package com.inventory.management.events;

import com.inventory.management.Enums.RequestStatus;
import com.inventory.management.Model.MaintenanceRequest;
import com.inventory.management.ReposiotryServices.MaintenanceRequestRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class MaintenanceRequestEventListener {
    private final MaintenanceRequestRepositoryService repositoryService;
    @EventListener
    @Transactional
    @Async
    public void handleMaintenanceRequestCreatedEvent(MaintenanceRequestCreatedEvent event){
        log.info("Processing maintenance request created event for request ID: {}", event.getRequetId());

        MaintenanceRequest maintenanceRequest = repositoryService.retriveRequestById(event.getRequetId());
        maintenanceRequest.setStatus(RequestStatus.IN_PROGRESS);
        maintenanceRequest.setScheduledDate(LocalDateTime.now().minusDays(1));
        repositoryService.saveMaintenanceRequest(maintenanceRequest);

    }

}
