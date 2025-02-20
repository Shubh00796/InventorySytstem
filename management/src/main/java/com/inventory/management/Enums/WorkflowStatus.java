package com.inventory.management.Enums;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

public enum WorkflowStatus {

    PENDING,
    RUNNING,
    FAILED,
    COMPLETE;

    // Corrected to use WorkflowStatus instead of IssueStatus
    private static final EnumMap<WorkflowStatus, Set<WorkflowStatus>> allowedTransitions =
            new EnumMap<>(WorkflowStatus.class);

    static {
        allowedTransitions.put(PENDING, Set.of(RUNNING, FAILED));
        allowedTransitions.put(RUNNING, Set.of(COMPLETE, FAILED));
        allowedTransitions.put(FAILED, Set.of(PENDING, RUNNING));
        allowedTransitions.put(COMPLETE, Collections.emptySet()); // No transitions from COMPLETE
    }

    public boolean canTransitionTo(WorkflowStatus newStatus) {
        return allowedTransitions.getOrDefault(this, Collections.emptySet()).contains(newStatus);
    }

}
