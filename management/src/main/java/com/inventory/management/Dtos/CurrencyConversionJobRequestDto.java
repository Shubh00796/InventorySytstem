package com.inventory.management.Dtos;

import com.inventory.management.Enums.ConversionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversionJobRequestDto {

    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amount;
    private ConversionType conversionType;
}
