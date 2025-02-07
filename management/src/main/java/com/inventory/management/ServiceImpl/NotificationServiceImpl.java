package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.NotificationRequestDTO;
import com.inventory.management.Dtos.NotificationResponseDTO;
import com.inventory.management.Mapper.NotificationMapper;
import com.inventory.management.Model.Notification;
import com.inventory.management.Repo.NotificationRepository;
import com.inventory.management.service.EmailService;
import com.inventory.management.service.NotificationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;
    private final EmailService emailService;

    @Override
    @Transactional
    @CircuitBreaker(name = "emailService", fallbackMethod = "fallbackSendNotification")
    @RateLimiter(name = "rateLimiter")
    public NotificationResponseDTO sendNotification(NotificationRequestDTO requestDTO) {
        Notification entity = createNotification(requestDTO, "PENDING");
        saveNotification(entity);
        sendEmailAsync(entity);
        updateNotificationStatus(entity, "SENT");
        return mapToResponse(entity);
    }

    private NotificationResponseDTO fallbackSendNotification(NotificationRequestDTO requestDTO, Throwable t) {
        log.error("Fallback triggered: Email sending failed. Saving as FAILED.");
        Notification entity = createNotification(requestDTO, "FAILED");
        saveNotification(entity);
        return mapToResponse(entity);
    }

    private Notification createNotification(NotificationRequestDTO requestDTO, String status) {
        log.info("Creating notification with status: {}", status);
        Notification entity = mapper.toEntity(requestDTO);
        entity.setStatus(status);
        return entity;
    }

    private void saveNotification(Notification entity) {
        log.info("Saving notification to database");
        notificationRepository.save(entity);
    }

    private void sendEmailAsync(Notification entity) {
        log.info("Starting email sending asynchronously for: {}", entity.getEmail());
        new Thread(() -> emailService.sendEmail(
                entity.getEmail(),
                entity.getMessage(),
                entity.getChannel(),
                entity.getSubject()
        )).start();
    }

    private void updateNotificationStatus(Notification entity, String status) {
        log.info("Updating notification status to: {}", status);
        entity.setStatus(status);
        saveNotification(entity);
    }

    private NotificationResponseDTO mapToResponse(Notification entity) {
        log.info("Mapping notification entity to response DTO");
        return mapper.toDTO(entity);
    }
}
