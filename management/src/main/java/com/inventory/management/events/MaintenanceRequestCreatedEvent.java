package com.inventory.management.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MaintenanceRequestCreatedEvent extends ApplicationEvent {
    private final Long requetId;

    public MaintenanceRequestCreatedEvent(Object source, Long requestId, Long requetId) {
        super(source);
        this.requetId = requetId;
    }
}
