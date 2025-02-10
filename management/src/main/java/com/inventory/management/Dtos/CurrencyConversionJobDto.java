package com.inventory.management.Dtos;

import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.ConversionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversionJobDto {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private ConversionType conversionType;
    private ConversionStatus conversionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}