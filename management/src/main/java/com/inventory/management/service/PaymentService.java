package com.inventory.management.service;

import com.inventory.management.Dtos.PaymentRequestDTO;
import com.inventory.management.Dtos.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO processPayment(PaymentRequestDTO request);

}
