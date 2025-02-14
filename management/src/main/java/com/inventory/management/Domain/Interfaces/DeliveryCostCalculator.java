package com.inventory.management.Domain.Interfaces;

import com.inventory.management.Domain.Entity.Ddelivery;

public interface DeliveryCostCalculator {
    double calculateCost(Ddelivery ddelivery);
}