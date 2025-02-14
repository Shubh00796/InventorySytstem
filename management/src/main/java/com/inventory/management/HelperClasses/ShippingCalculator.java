package com.inventory.management.HelperClasses;

import com.inventory.management.DesignPatternInterfaces.ShippingCarrier;

public abstract class ShippingCalculator {

    protected ShippingCarrier shippingCarrier;

    protected ShippingCalculator(ShippingCarrier shippingCarrier) {
        this.shippingCarrier = shippingCarrier;
    }

    public abstract double calculateCost(double weight, double distance);
}
