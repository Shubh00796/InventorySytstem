package com.inventory.management.DesignPatternInterfaces;

import org.springframework.stereotype.Component;

@Component
public class FedExCarrier implements ShippingCarrier {

    private static final double BASE_COST = 5.0;
    private static final double WEIGHT_MULTIPLIER = 0.75;
    private static final double DISTANCE_MULTIPLIER = 0.06;

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