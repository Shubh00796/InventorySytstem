package com.inventory.management.events;

import com.inventory.management.Enums.WorkflowStatus;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * An event representing a state change in a workflow.
 * <p>
 * This immutable event contains the workflow's identifier and the transition details.
 * </p>
 */
@Getter
public class WorkflowStateChangeEvent extends ApplicationEvent {

    private final Long workflowId;
    private final WorkflowStatus oldStatus;
    private final WorkflowStatus newStatus;

    /**
     * Constructs a new {@code WorkflowStateChangeEvent}.
     *
     * @param source     the object on which the event initially occurred (never {@code null})
     * @param workflowId the identifier of the workflow
     * @param oldStatus  the previous state of the workflow
     * @param newStatus  the new state of the workflow
     */
    public WorkflowStateChangeEvent(Object source, Long workflowId, WorkflowStatus oldStatus, WorkflowStatus newStatus) {
        super(source);
        this.workflowId = workflowId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }
}
