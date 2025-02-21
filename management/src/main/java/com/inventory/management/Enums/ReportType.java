package com.inventory.management.Enums;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

public enum ReportType {
        ISSUE_TREND,
    PROJECT_PROGRESS,
    PERFORMANCE_METRICS;

    private static final EnumMap<ReportType, Set<ReportType>> allowedTransitions = new EnumMap<>(ReportType.class);

    static {
        allowedTransitions.put(ISSUE_TREND, Set.of(PROJECT_PROGRESS));
        allowedTransitions.put(PROJECT_PROGRESS, Set.of(PERFORMANCE_METRICS));
        allowedTransitions.put(PERFORMANCE_METRICS, Collections.emptySet());
    }

    public boolean canTransitionTo(ReportType newType) {
        return allowedTransitions.getOrDefault(this, Collections.emptySet()).contains(newType);
    }
    public ReportType getNextStatus() {
        return allowedTransitions.getOrDefault(this, Collections.emptySet())
                .stream()
                .findFirst()
                .orElse(null); // Returns null if it's the final state
    }
}
