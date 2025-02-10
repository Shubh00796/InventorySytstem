package com.inventory.management.Model;

import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.ConversionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency_conversion_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversionJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    @Enumerated(EnumType.STRING)
    private ConversionType conversionType;

    @Enumerated(EnumType.STRING)
    private ConversionStatus conversionStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}