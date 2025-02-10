package com.inventory.management.Utility;

import com.inventory.management.Dtos.CurrencyConversionJobDto;
import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.ConversionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyConversionFallbackUtil {

    public static CurrencyConversionJobDto fallbackJob(Long id, Throwable t) {
        return CurrencyConversionJobDto.builder()
                .id(id)
                .sourceCurrency("N/A")
                .targetCurrency("N/A")
                .amount(BigDecimal.ZERO)
                .convertedAmount(BigDecimal.ZERO)
                .conversionType(ConversionType.REALTIME) // Default fallback type
                .conversionStatus(ConversionStatus.FAILED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}