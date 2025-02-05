package com.inventory.management.service;


import com.inventory.management.Dtos.InventoryItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryItemService {

    InventoryItemDTO addInventoryItem(InventoryItemDTO inventoryItemDTO);

    InventoryItemDTO updateInventoryItem(String productCode, InventoryItemDTO inventoryItemDTO);

//    List<InventoryItemDTO> getAllInventoryItems();

    List<InventoryItemDTO> getInventoryItemByProductCode(String productCode, InventoryItemDTO inventoryItemDTO);

    List<InventoryItemDTO> getItemsByCategory(String category);

    List<InventoryItemDTO> getActiveItems();

    List<InventoryItemDTO> getInactiveItems();

    List<InventoryItemDTO> getItemsByPriceRange(Double minPrice, Double maxPrice);

    List<InventoryItemDTO> getItemsBySupplier(String supplier);

    List<InventoryItemDTO> getItemsByProductNameContaining(String productName);

    Page<InventoryItemDTO> getPaginatedInventoryItems(Pageable pageable);
}
