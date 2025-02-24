package com.inventory.management.events;

import com.inventory.management.Model.EnergyMeter;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EnergyThresholdExceededEvent extends ApplicationEvent {
    private final EnergyMeter energyMeter;

    public EnergyThresholdExceededEvent(EnergyMeter energyMeter) {
        super(energyMeter);
        this.energyMeter = energyMeter;
    }
}
