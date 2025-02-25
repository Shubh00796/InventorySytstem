package com.inventory.management.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FlightPathOptimizationEvent extends ApplicationEvent {
    private final Long flightPathId;


    public FlightPathOptimizationEvent(Object source, Long flightPathId) {
        super(source);
        this.flightPathId = flightPathId;
    }

    public Long getFlightPathId() {
        return flightPathId;
    }
}
