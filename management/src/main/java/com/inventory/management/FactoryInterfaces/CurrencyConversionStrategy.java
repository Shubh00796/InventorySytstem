package com.inventory.management.FactoryInterfaces;

import java.math.BigDecimal;

public interface CurrencyConversionStrategy {
    BigDecimal convert(BigDecimal amount, String sourceCurrency, String targetCurrency);
}