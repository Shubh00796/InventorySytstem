package com.inventory.management.fallbacks;

import com.inventory.management.Dtos.InventoryItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class InventoryFallbackHandler {

    public InventoryItemDTO handleAddItemFailure(InventoryItemDTO dto, Throwable t) {
        log.error("Fallback - addInventoryItem: {}", t.getMessage());
        return new InventoryItemDTO();
    }

    public InventoryItemDTO handleUpdateItemFailure(String productCode, InventoryItemDTO dto, Throwable t) {
        log.error("Fallback - updateInventoryItem: {}", t.getMessage());
        return new InventoryItemDTO();
    }

    public List<InventoryItemDTO> handleItemByCodeFailure(String productCode, Throwable t) {
        log.error("Fallback - getInventoryItemByProductCode: {}", t.getMessage());
        return Collections.emptyList();
    }

    public List<InventoryItemDTO> handleCategoryFailure(String category, Throwable t) {
        log.error("Fallback - getItemsByCategory: {}", t.getMessage());
        return Collections.emptyList();
    }

    public List<InventoryItemDTO> handleActiveItemsFailure(Throwable t) {
        log.error("Fallback - getActiveItems: {}", t.getMessage());
        return Collections.emptyList(); // return empty list on failure
    }

    public Page<InventoryItemDTO> handlePaginatedFailure(Pageable pageable, Throwable t) {
        log.error("Fallback - getPaginatedInventoryItems: {}", t.getMessage());
        return Page.empty();
    }
}
