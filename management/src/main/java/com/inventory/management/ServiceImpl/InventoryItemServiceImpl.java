package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.InventoryItemDTO;
import com.inventory.management.Mapper.InventoryMapper;
import com.inventory.management.Model.InventoryItem;
import com.inventory.management.Repo.InventoryItemRepository;
import com.inventory.management.fallbacks.InventoryFallbackHandler;
import com.inventory.management.service.InventoryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryMapper mapper;
    private final InventoryFallbackHandler fallbackHandler;

    private static final String INVENTORY_SERVICE = "inventoryService";

    @Override
    @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "fallbackHandler.handleAddItemFailure")
    @RateLimiter(name = "inventoryRateLimiter")
    public InventoryItemDTO addInventoryItem(InventoryItemDTO inventoryItemDTO) {
        log.info("Adding new inventory item: {}", inventoryItemDTO.getProductCode());

        if (inventoryItemRepository.findByProductCode(inventoryItemDTO.getProductCode()).isPresent()) {
            throw logAndThrow("Product code must be unique: " + inventoryItemDTO.getProductCode());
        }

        InventoryItem item = inventoryItemRepository.save(mapper.toEntity(inventoryItemDTO));
        log.info("Inventory item successfully added: {}", item.getProductCode());
        return mapper.toDTO(item);
    }

    @Override
    @CacheEvict(value = "inventoryItems", key = "#productCode")
    @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "fallbackHandler.handleUpdateItemFailure")
    @RateLimiter(name = "inventoryRateLimiter")
    public InventoryItemDTO updateInventoryItem(String productCode, InventoryItemDTO inventoryItemDTO) {
        log.info("Updating inventory item: {}", productCode);

        InventoryItem item = inventoryItemRepository.findByProductCode(productCode)
                .orElseThrow(() -> logAndThrow("Product not found: " + productCode));

        updateInventoryDetails(item, inventoryItemDTO);

        InventoryItem updatedItem = inventoryItemRepository.save(item);
        log.info("Inventory item updated: {}", updatedItem.getProductCode());
        return mapper.toDTO(updatedItem);
    }

    @Override
    @Cacheable(value = "inventoryItems", key = "#productCode", unless = "#result == null")
    @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "fallbackHandler.handleItemByCodeFailure")
    @RateLimiter(name = "inventoryRateLimiter")
    public List<InventoryItemDTO> getInventoryItemByProductCode(String productCode) {
        log.info("Fetching inventory item: {}", productCode);

        return inventoryItemRepository.findByProductCode(productCode)
                .map(item -> List.of(mapper.toDTO(item)))
                .orElseThrow(() -> logAndThrow("Product not found: " + productCode));
    }

    @Override
    @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "fallbackHandler.handleCategoryFailure")
    @RateLimiter(name = "inventoryRateLimiter")
    public List<InventoryItemDTO> getItemsByCategory(String category) {
        log.info("Fetching inventory items for category: {}", category);
        return inventoryItemRepository.findByCategory(category)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "fallbackHandler.handleActiveItemsFailure")
    @RateLimiter(name = "inventoryRateLimiter")
    public List<InventoryItemDTO> getActiveItems() {
        log.info("Fetching active inventory items");
        return inventoryItemRepository.findByIsActiveTrue()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryItemDTO> getInactiveItems() {
        log.info("Fetching inactive inventory items");
        return inventoryItemRepository.findByIsActiveFalse()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryItemDTO> getItemsByPriceRange(Double minPrice, Double maxPrice) {
        log.info("Fetching inventory items within price range: {} - {}", minPrice, maxPrice);
        return inventoryItemRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<InventoryItemDTO> getItemsBySupplier(String supplier) {
        log.info("Fetching inventory items by supplier: {}", supplier);
        return inventoryItemRepository.findBySupplier(supplier)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryItemDTO> getItemsByProductNameContaining(String productName) {
        log.info("Fetching inventory items containing product name: {}", productName);
        return inventoryItemRepository.findByProductNameContainingIgnoreCase(productName)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "fallbackHandler.handlePaginatedFailure")
    @RateLimiter(name = "inventoryRateLimiter")
    public Page<InventoryItemDTO> getPaginatedInventoryItems(Pageable pageable) {
        log.info("Fetching paginated inventory items");
        return inventoryItemRepository.findAll(pageable).map(mapper::toDTO);
    }

    private void updateInventoryDetails(InventoryItem item, InventoryItemDTO dto) {
        if (dto.getQuantity() != null) {
            item.setQuantity(dto.getQuantity());
        }
        if (dto.getPrice() != null) {
            item.setPrice(dto.getPrice());
        }
    }

    private RuntimeException logAndThrow(String message) {
        log.error(message);
        return new RuntimeException(message);
    }
}
