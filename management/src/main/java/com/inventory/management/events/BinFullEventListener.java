package com.inventory.management.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BinFullEventListener {

    @Async
    @EventListener
            (
                    value = Exception.class
            )
    public void handleBinFullEvent(BinFullEvent event) {
        log.info("Processing BinFullEvent for bin id: {}", event.getBin().getId());
        try {
            // Simulate the pickup scheduling logic which might fail transiently.
            simulatePickupScheduling(event.getBin().getId());
            log.info("Successfully processed BinFullEvent for bin id: {}", event.getBin().getId());
        } catch (Exception e) {
            log.error("Error processing BinFullEvent for bin id: {}. Error: {}", event.getBin().getId(), e.getMessage());
            // Rethrow exception to trigger the retry mechanism.
            throw e;
        }
    }

    /**
     * Simulates the scheduling of a pickup for a full bin.
     * This method randomly fails to demonstrate the retry mechanism.
     */
    private void simulatePickupScheduling(Long binId) {
        if (Math.random() < 0.3) {  // 30% chance to simulate a transient error
            throw new RuntimeException("Simulated transient error during pickup scheduling for bin " + binId);
        }
        log.info("Pickup scheduled for bin id: {}", binId);

    }
}
