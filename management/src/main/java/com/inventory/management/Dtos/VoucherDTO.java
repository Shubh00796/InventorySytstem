package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VoucherDTO {
    private Long id;
    private String code;
    private String voucherType;
    private Double discountValue;
    private LocalDate expirationDate;
    private String description;
}