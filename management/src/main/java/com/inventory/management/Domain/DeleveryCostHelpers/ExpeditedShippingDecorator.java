package com.inventory.management.Domain.DeleveryCostHelpers;

import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.Interfaces.DeliveryCostCalculator;

public class ExpeditedShippingDecorator extends DeliveryCostCalculatorDecorator {
    private final double expeditedSurcharge;

    public ExpeditedShippingDecorator(DeliveryCostCalculator calculator, double expeditedSurcharge) {
        super(calculator);
        this.expeditedSurcharge = expeditedSurcharge;
    }

    @Override
    public double calculateCost(Ddelivery ddelivery) {
        double baseCost = super.calculateCost(ddelivery);
        return ddelivery.isExpeditedShipping() ? baseCost + expeditedSurcharge : baseCost;
    }
}