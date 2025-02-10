package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.CurrencyConversionStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class HistoricalConversionStrategy implements CurrencyConversionStrategy {

    @Override
    public BigDecimal convert(BigDecimal amount, String sourceCurrency, String targetCurrency) {
        BigDecimal rate = BigDecimal.valueOf(0.95);
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}