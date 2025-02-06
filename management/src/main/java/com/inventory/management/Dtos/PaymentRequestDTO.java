package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private String token;
    private Long amount;
    private String currency;
}