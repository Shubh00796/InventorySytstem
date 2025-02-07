package com.inventory.management.Mapper;

import com.inventory.management.Dtos.InventoryItemDTO;
import com.inventory.management.Dtos.NotificationRequestDTO;
import com.inventory.management.Dtos.NotificationResponseDTO;
import com.inventory.management.Model.InventoryItem;
import com.inventory.management.Model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toEntity(NotificationRequestDTO dto);
    NotificationResponseDTO toDTO(Notification entity);
}