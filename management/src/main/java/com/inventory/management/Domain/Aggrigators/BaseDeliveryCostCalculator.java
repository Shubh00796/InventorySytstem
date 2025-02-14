package com.inventory.management.Domain.Aggrigators;

import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.Interfaces.DeliveryCostCalculator;


public class BaseDeliveryCostCalculator implements DeliveryCostCalculator {
    private final double ratePerKg;

    public BaseDeliveryCostCalculator(double ratePerKg) {
        this.ratePerKg = ratePerKg;
    }

    @Override
    public double calculateCost(Ddelivery ddelivery) {
        return Math.max(ddelivery.getPackageWeight(), 0) * ratePerKg;
    }
}