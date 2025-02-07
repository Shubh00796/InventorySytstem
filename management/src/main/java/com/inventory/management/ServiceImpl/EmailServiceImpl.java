package com.inventory.management.ServiceImpl;

import com.inventory.management.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Override
    @Async

    public void sendEmail(String to, String message, String channel, String subject) {
        log.info("Sending email to {}: {}", to, message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Email sent successfully to {}", to);
    }
}
