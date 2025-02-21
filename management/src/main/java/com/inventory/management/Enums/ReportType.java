package com.inventory.management.Enums;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

public enum IssueStatus {

    PENDING,
    RUNNING,
    FAILED,
    COMPLETE;

    private static final EnumMap<IssueStatus, Set<IssueStatus>> allowedTransitions = new EnumMap<>(IssueStatus.class);

    static {
        allowedTransitions.put(PENDING, Set.of(RUNNING, FAILED));
        allowedTransitions.put(RUNNING, Set.of(COMPLETE, FAILED));
        allowedTransitions.put(FAILED, Set.of(PENDING, RUNNING));
        allowedTransitions.put(COMPLETE, Collections.emptySet()); // No transitions from COMPLETE
    }

    public boolean canTransitionTo(IssueStatus newStatus) {
        return allowedTransitions.getOrDefault(this, Collections.emptySet()).contains(newStatus);
    }
}
