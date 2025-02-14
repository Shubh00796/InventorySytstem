package com.inventory.management.Domain.DeleveryCostHelpers;

import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.Interfaces.DeliveryCostCalculator;

public class FragileHandlingDecorator extends DeliveryCostCalculatorDecorator {
    private final double fragileSurcharge;

    public FragileHandlingDecorator(DeliveryCostCalculator calculator, double fragileSurcharge) {
        super(calculator);
        this.fragileSurcharge = fragileSurcharge;
    }

    @Override
    public double calculateCost(Ddelivery ddelivery) {
        double baseCost = super.calculateCost(ddelivery);
        return ddelivery.isFragileHandling() ? baseCost + fragileSurcharge : baseCost;
    }
}