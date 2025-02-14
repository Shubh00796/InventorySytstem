package com.inventory.management.DesignPatternsImpl;

import com.inventory.management.DesignPatternInterfaces.ShippingCarrier;
import org.springframework.stereotype.Component;

@Component
public class UPSCarrier implements ShippingCarrier {

    private static final double BASE_COST = 4.5;
    private static final double WEIGHT_MULTIPLIER = 0.85;
    private static final double DISTANCE_MULTIPLIER = 0.045;

    @Override
    public double calculateBaseCost(double weight, double distance) {
        validateInputs(weight, distance);
        return BASE_COST + (WEIGHT_MULTIPLIER * weight) + (DISTANCE_MULTIPLIER * distance);
    }

    private void validateInputs(double weight, double distance) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero.");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than zero.");
        }
    }
}