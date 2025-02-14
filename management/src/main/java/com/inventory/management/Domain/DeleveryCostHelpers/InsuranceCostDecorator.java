package com.inventory.management.Domain.DeleveryCostHelpers;

import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.Interfaces.DeliveryCostCalculator;
public class InsuranceCostDecorator extends DeliveryCostCalculatorDecorator {
    private final double insuranceRate;

    public InsuranceCostDecorator(DeliveryCostCalculator calculator, double insuranceRate) {
        super(calculator);
        this.insuranceRate = insuranceRate;
    }

    @Override
    public double calculateCost(Ddelivery ddelivery) {
        double baseCost = super.calculateCost(ddelivery);
        return ddelivery.isInsurance() ? baseCost + (baseCost * insuranceRate) : baseCost;
    }
}