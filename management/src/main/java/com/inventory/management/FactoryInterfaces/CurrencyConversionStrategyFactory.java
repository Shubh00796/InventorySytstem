package com.inventory.management.FactoryInterfaces;

import com.inventory.management.Enums.ConversionType;

public interface CurrencyConversionStrategyFactory {
    CurrencyConversionStrategy getCurrencyConversionStrategy(ConversionType conversionType);
}