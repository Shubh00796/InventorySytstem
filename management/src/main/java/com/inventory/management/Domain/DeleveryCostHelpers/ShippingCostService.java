package com.inventory.management.Domain.DeleveryCostHelpers;

import com.inventory.management.Domain.Aggrigators.BaseDeliveryCostCalculator;
import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.Interfaces.DeliveryCostCalculator;
import org.springframework.stereotype.Component;

@Component
public class ShippingCostService {
    private static final double BASE_RATE_PER_KG = 10.0;
    private static final double INSURANCE_RATE = 0.05;
    private static final double EXPEDITED_SURCHARGE = 20.0;
    private static final double FRAGILE_SURCHARGE = 15.0;

    public double calculateShippingCost(Ddelivery ddelivery) {
        DeliveryCostCalculator calculator = new BaseDeliveryCostCalculator(BASE_RATE_PER_KG);

        if (ddelivery.isInsurance()) {
            calculator = new InsuranceCostDecorator(calculator,INSURANCE_RATE);
        }
        if (ddelivery.isExpeditedShipping()) {
            calculator = new ExpeditedShippingDecorator(calculator,EXPEDITED_SURCHARGE);
        }
        if (ddelivery.isFragileHandling()) {
            calculator = new FragileHandlingDecorator(calculator,FRAGILE_SURCHARGE);
        }

        return calculator.calculateCost(ddelivery);
    }
}