package com.inventory.management.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PaymentLoggingAspect {

    @Pointcut("execution(* com.inventory.management.ServiceImpl.PaymentServiceImpl.processPayment(..))")
    public void paymentProcessingPointcut() {}

    @AfterThrowing(pointcut = "paymentProcessingPointcut()", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        log.error("Payment processing failed: {}", ex.getMessage());
    }
}
