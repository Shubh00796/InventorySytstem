package com.inventory.management.listener;

import com.inventory.management.events.WorkflowStateChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j

public class WorkflowStateChangeListener {

    @EventListener
    public void handleWorkflowStateChange(WorkflowStateChangeEvent event) {
        log.info("Workflow ID {} state changed from {} to {}",
                event.getWorkflowId(), event.getOldStatus(), event.getNewStatus());

        // Example: Notify an external service, update cache, trigger async jobs, etc.
    }
}