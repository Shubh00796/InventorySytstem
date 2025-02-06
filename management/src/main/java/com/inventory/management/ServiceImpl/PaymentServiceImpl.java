package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.PaymentRequestDTO;
import com.inventory.management.Dtos.PaymentResponseDTO;
import com.inventory.management.Exceptions.PaymentProcessingException;
import com.inventory.management.Mapper.PaymentMapper;
import com.inventory.management.Model.Payment;
import com.inventory.management.Repo.PaymentRepository;
import com.inventory.management.service.PaymentService;
import com.inventory.management.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;
    private final PaymentMapper paymentMapper;


    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Override
    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        PaymentResponseDTO response = new PaymentResponseDTO();
        Charge charge = null;

        try {
            charge = stripeService.createCharge(
                    request.getAmount(),
                    request.getCurrency(),
                    request.getToken(),
                    "Payment Charge"
            );
            log.info("Stripe charge created with id: {}", charge.getId());

            Payment payment = paymentMapper.toPayment(request);
            payment.setStripeChargeId(charge.getId());
            payment.setCreatedAt(LocalDateTime.now());
            Payment savedPayment = paymentRepository.save(payment);
            log.info("Payment record saved with id: {}", payment.getId());

            response.setStatus("SUCCESS");
            response.setMessage("Payment processed successfully.");

            return paymentMapper.toPaymentResponse(savedPayment);

        } catch (StripeException e) {
            log.error("Error processing payment: {}", e.getMessage());

            if (charge != null) {
                try {
                    Refund refund = stripeService.refundCharge(charge.getId());
                    log.info("Refund issued for charge id {}: refund id {}", charge.getId(), refund.getId());
                } catch (StripeException stripeEx) {
                    log.error("Error refunding charge {}: {}", charge.getId(), stripeEx.getMessage());
                }
            }

            response.setStatus("FAILURE");
            response.setMessage("Payment processing failed: " + e.getMessage());

            return response;
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());

            response.setStatus("ERROR");
            response.setMessage("Unexpected error occurred during payment processing.");

            return response;
        }
    }


}