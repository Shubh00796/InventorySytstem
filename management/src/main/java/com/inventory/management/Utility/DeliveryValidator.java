package com.inventory.management.Utility;

import com.inventory.management.Dtos.DeliveryDTO;
import com.inventory.management.Model.Delivery;
import com.inventory.management.Repo.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeliveryValidator {

    private final DeliveryRepository deliveryRepository;

    public void validateDeliveryDTO(DeliveryDTO deliveryDTO) {
        if (deliveryExistsByTrackingNumber(deliveryDTO.getTrackingNumber())) {
            throw new IllegalArgumentException("Tracking number already exists!");
        }
        if (isNullOrEmpty(deliveryDTO.getCourier())) {
            throw new IllegalArgumentException("Courier cannot be empty!");
        }
        if (!isValidStatus(deliveryDTO.getStatus())) {
            throw new IllegalArgumentException("Invalid delivery status!");
        }
        if (isNullOrEmpty(deliveryDTO.getPickupAddress())) {
            throw new IllegalArgumentException("Pickup address cannot be empty!");
        }
        if (isNullOrEmpty(deliveryDTO.getDeliveryAddress())) {
            throw new IllegalArgumentException("Delivery address cannot be empty!");
        }
        if (isInvalidEstimatedDeliveryTime(deliveryDTO.getEstimatedDeliveryTime())) {
            throw new IllegalArgumentException("Estimated delivery time cannot be in the past!");
        }
    }

    public void validateTrackingNumber(Long id, String trackingNumber) {
        if (trackingNumber != null && trackingNumberExistsForDifferentId(id, trackingNumber)) {
            throw new IllegalArgumentException("Tracking number is already in use");
        }
    }

    public void validateStatusTransition(String currentStatus, String newStatus) {
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new IllegalStateException("Invalid status transition: " + currentStatus + " -> " + newStatus);
        }
    }

    private boolean deliveryExistsByTrackingNumber(String trackingNumber) {
        return deliveryRepository.findByTrackingNumber(trackingNumber).isPresent();
    }

    private boolean trackingNumberExistsForDifferentId(Long id, String trackingNumber) {
        Optional<Delivery> existingDelivery = deliveryRepository.findByTrackingNumber(trackingNumber);
        return existingDelivery.isPresent() && !existingDelivery.get().getId().equals(id);
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidStatus(String status) {
        return List.of("PENDING", "IN_TRANSIT", "DELIVERED", "CANCELLED").contains(status);
    }

    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        Map<String, List<String>> validTransitions = Map.of(
                "PENDING", List.of("IN_TRANSIT", "CANCELLED"),
                "IN_TRANSIT", List.of("DELIVERED", "CANCELLED"),
                "DELIVERED", List.of(),
                "CANCELLED", List.of()
        );
        return validTransitions.getOrDefault(currentStatus, List.of()).contains(newStatus);
    }

    private boolean isInvalidEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        return estimatedDeliveryTime != null && estimatedDeliveryTime.isBefore(LocalDateTime.now());
    }
}
