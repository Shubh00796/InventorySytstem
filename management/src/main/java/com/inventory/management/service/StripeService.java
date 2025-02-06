package com.inventory.management.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class    StripeService {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
        if (stripeSecretKey == null || stripeSecretKey.isEmpty()) {
            log.error("Stripe Secret Key is not set or is empty.");
        }
    }

    public Charge createCharge(Long amount, String currency, String source, String description) throws StripeException {
        Map<String, Object> chargeParams = buildChargeParams(amount, currency, source, description);
        try {
            Charge charge = Charge.create(chargeParams);
            log.info("Charge created successfully with ID: {}", charge.getId());
            return charge;
        } catch (StripeException e) {
            log.error("Error creating charge: {}", e.getMessage());
            throw e;
        }
    }

    public Refund refundCharge(String chargeId) throws StripeException {
        Map<String, Object> refundParams = new HashMap<>();
        refundParams.put("charge", chargeId);
        try {
            Refund refund = Refund.create(refundParams);
            log.info("Refund created successfully for Charge ID: {}", chargeId);
            return refund;
        } catch (StripeException e) {
            log.error("Error processing refund: {}", e.getMessage());
            throw e;
        }
    }

    private Map<String, Object> buildChargeParams(Long amount, String currency, String source, String description) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", currency);
        chargeParams.put("source", source);
        chargeParams.put("description", description);
        return chargeParams;
    }
}
