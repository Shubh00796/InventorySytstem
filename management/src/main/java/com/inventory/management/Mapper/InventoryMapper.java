package com.inventory.management.Mapper;

import com.inventory.management.Dtos.InventoryItemDTO;
import com.inventory.management.Model.InventoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryItem toEntity(InventoryItemDTO inventoryItemDTO);
    InventoryItemDTO toDTO(InventoryItem inventoryItem);

    @Mapping(target = "id", ignore = true) // Do not map 'id'
    @Mapping(target = "productCode", ignore = true) // Do not map 'productCode'
    @Mapping(target = "productName", ignore = true) // Do not map 'productName'
    @Mapping(target = "quantity", source = "quantity") // Direct mapping
    @Mapping(target = "price", source = "price") // Direct mapping
    InventoryItem updateToEntity(InventoryItemDTO inventoryItemDTO);
}