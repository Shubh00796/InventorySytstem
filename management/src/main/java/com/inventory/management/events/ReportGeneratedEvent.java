package com.inventory.management.events;

import com.inventory.management.Enums.ReportType;
import com.inventory.management.Enums.WorkflowStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

/**
 * An event representing a state change in a workflow.
 * <p>
 * This immutable event contains the workflow's identifier and the transition details.
 * </p>
 */
@Getter
public class ReportGeneratedEvent extends ApplicationEvent {




    /**
     * Constructs a new {@code WorkflowStateChangeEvent}.
     *
     * @param source    the object on which the event initially occurred (never {@code null})
     * @param reportId  the identifier of the workflow
     * @param oldStatus the previous state of the workflow
     * @param newStatus the new state of the workflow
     */


        private final Long reportId;
        private final ReportType oldStatus;
        private final ReportType newStatus;

        public ReportGeneratedEvent(Object source, Long reportId, ReportType oldStatus, ReportType newStatus) {
            super(source);
            this.reportId = reportId;
            this.oldStatus = oldStatus;
            this.newStatus = newStatus;
        }
    }
