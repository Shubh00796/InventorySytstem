package com.inventory.management.service;

import com.inventory.management.Dtos.PaymentRequestDTO;
import com.inventory.management.Dtos.PaymentResponseDTO;
import com.inventory.management.Model.Payment;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO processPayment(PaymentRequestDTO request);
    List<Payment> getPaymentsByStripeId(String chargeId);
    

}
