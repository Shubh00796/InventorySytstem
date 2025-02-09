package com.inventory.management.service;

import com.inventory.management.Dtos.DeliveryDTO;

import java.util.List;

public interface DeliveryService {
    List<DeliveryDTO> getAllDeliveries();
    DeliveryDTO getDeliveryById(Long id);
    DeliveryDTO createDelivery(DeliveryDTO deliveryDTO);
    DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO);
    DeliveryDTO getByTrackingNumber(String trackingNumber);


    void deleteDelivery(Long id);
}
