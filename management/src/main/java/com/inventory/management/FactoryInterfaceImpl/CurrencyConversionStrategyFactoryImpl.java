package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.Enums.ConversionType;
import com.inventory.management.FactoryInterfaces.CurrencyConversionStrategy;
import com.inventory.management.FactoryInterfaces.CurrencyConversionStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConversionStrategyFactoryImpl implements CurrencyConversionStrategyFactory {

    private final RealTimeConversionStrategy realTimeConversionStrategy;
    private final CachedConversionStrategy cachedConversionStrategy;
    private final HistoricalConversionStrategy historicalConversionStrategy;

    @Autowired
    public CurrencyConversionStrategyFactoryImpl(RealTimeConversionStrategy realTimeConversionStrategy,
                                                 CachedConversionStrategy cachedConversionStrategy,
                                                 HistoricalConversionStrategy historicalConversionStrategy) {
        this.realTimeConversionStrategy = realTimeConversionStrategy;
        this.cachedConversionStrategy = cachedConversionStrategy;
        this.historicalConversionStrategy = historicalConversionStrategy;
    }

    @Override
    public CurrencyConversionStrategy getCurrencyConversionStrategy(ConversionType conversionType) {
        return getConversionStrategy(conversionType);
    }

    private CurrencyConversionStrategy getConversionStrategy(ConversionType conversionType) {
        switch (conversionType) {
            case REALTIME:
                return realTimeConversionStrategy;
            case CACHED:
                return cachedConversionStrategy;
            case HISTORICAL:
                return historicalConversionStrategy;
            default:
                throw new IllegalArgumentException("Unsupported conversion type: " + conversionType);
        }
    }
}