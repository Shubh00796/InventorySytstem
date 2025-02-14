package com.inventory.management.HelperClasses;

import com.inventory.management.DesignPatternInterfaces.ShippingCarrier;

public class ExpressShippingCalculator extends ShippingCalculator {

    private static final double MARKUP_RATE = 1.25;

    public ExpressShippingCalculator(ShippingCarrier shippingCarrier) {
        super(shippingCarrier);
    }

    @Override
    public double calculateCost(double weight, double distance) {
        validateInputs(weight, distance);
        double baseCost = shippingCarrier.calculateBaseCost(weight, distance);
        return baseCost * MARKUP_RATE;
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
