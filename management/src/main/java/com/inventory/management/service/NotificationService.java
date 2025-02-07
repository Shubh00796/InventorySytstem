package com.inventory.management.service;

import com.inventory.management.Dtos.NotificationRequestDTO;
import com.inventory.management.Dtos.NotificationResponseDTO;

public interface NotificationService {
    NotificationResponseDTO sendNotification(NotificationRequestDTO requestDTO);

}
