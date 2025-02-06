package com.inventory.management.Controller;

import com.inventory.management.Dtos.PaymentRequestDTO;
import com.inventory.management.Dtos.PaymentResponseDTO;
import com.inventory.management.Exceptions.PaymentProcessingException;
import com.inventory.management.Model.Payment;
import com.inventory.management.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody @Valid PaymentRequestDTO request) {
        try {
            PaymentResponseDTO response = paymentService.processPayment(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PaymentProcessingException e) {
            return new ResponseEntity<>(new PaymentResponseDTO("FAILURE", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new PaymentResponseDTO("ERROR", "Unexpected error occurred during payment processing."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-stripe-id/{chargeId}")
    public ResponseEntity<List<Payment>> getPaymentsByStripeId(@PathVariable String chargeId) {
        List<Payment> payments = paymentService.getPaymentsByStripeId(chargeId);
        return ResponseEntity.ok(payments);
    }
}
