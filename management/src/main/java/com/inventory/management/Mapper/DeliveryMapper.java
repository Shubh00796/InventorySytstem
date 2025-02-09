package com.inventory.management.Mapper;

import com.inventory.management.Dtos.DeliveryDTO;
import com.inventory.management.Dtos.NotificationRequestDTO;
import com.inventory.management.Dtos.NotificationResponseDTO;
import com.inventory.management.Model.Delivery;
import com.inventory.management.Model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    Delivery toEntity(DeliveryDTO dto);
    DeliveryDTO toDTO(Delivery entity);
}