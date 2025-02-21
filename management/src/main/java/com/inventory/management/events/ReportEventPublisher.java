package com.inventory.management.events;

import com.inventory.management.Enums.WorkflowStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publisher for workflow state change events.
 * <p>
 * This component is responsible for publishing {@link WorkflowStateChangeEvent} to notify listeners about
 * changes in workflow states.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class WorkflowEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Publishes a state change event for a given workflow.
     *
     * @param workflowId the identifier of the workflow
     * @param oldStatus  the previous state of the workflow
     * @param newStatus  the new state of the workflow
     */
    public void publishStateChangeEvent(Long workflowId, WorkflowStatus oldStatus, WorkflowStatus newStatus) {
        eventPublisher.publishEvent(
                new WorkflowStateChangeEvent(this, workflowId, oldStatus, newStatus)
        );
    }
}
