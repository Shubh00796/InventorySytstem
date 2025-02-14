package com.inventory.management.Domain.DeleveryCostHelpers;

import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.Interfaces.DeliveryCostCalculator;

public abstract class DeliveryCostCalculatorDecorator implements DeliveryCostCalculator {
    protected final DeliveryCostCalculator calculator;

    public DeliveryCostCalculatorDecorator(DeliveryCostCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public double calculateCost(Ddelivery ddelivery) {
        return calculator.calculateCost(ddelivery);
    }
}