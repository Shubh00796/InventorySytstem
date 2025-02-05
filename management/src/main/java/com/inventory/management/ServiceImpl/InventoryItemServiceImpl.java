package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.InventoryItemDTO;
import com.inventory.management.Mapper.InventoryMapper;
import com.inventory.management.Model.InventoryItem;
import com.inventory.management.Repo.InventoryItemRepository;
import com.inventory.management.service.InventoryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryMapper mapper;

    @Override
    public InventoryItemDTO addInventoryItem(InventoryItemDTO inventoryItemDTO) {
        log.info("Adding new inventory item: {}", inventoryItemDTO.getProductCode());
        try {
            Optional<InventoryItem> existingItem = inventoryItemRepository.findByProductCode(inventoryItemDTO.getProductCode());
            if (existingItem.isPresent()) {
                log.error("Product with product code {} already exists", inventoryItemDTO.getProductCode());
                throw new RuntimeException("Product code must be unique: " + inventoryItemDTO.getProductCode());
            }
            InventoryItem itemSavedToEntity = mapper.toEntity(inventoryItemDTO);
            InventoryItem itemSavedToRepo = inventoryItemRepository.save(itemSavedToEntity);
            log.info("Inventory item successfully added: {}", itemSavedToRepo.getProductCode());
            return mapper.toDTO(itemSavedToRepo);
        } catch (Exception e) {
            log.error("Error occurred while adding inventory item", e);
            throw new RuntimeException("Error occurred while adding the inventory item", e);
        }
    }

    @Override
    @CacheEvict(value = "inventoryItems", key = "#productCode")
    public InventoryItemDTO updateInventoryItem(String productCode, InventoryItemDTO inventoryItemDTO) {
        log.info("Updating inventory item with product code: {}", productCode);
        try {
            InventoryItem existingItem = inventoryItemRepository.findByProductCode(productCode)
                    .orElseThrow(() -> {
                        log.error("Product with product code {} not found", productCode);
                        return new RuntimeException("The product you want to update is not present");
                    });

            InventoryItem updatedItem = mapper.updateToEntity(inventoryItemDTO);
            if (updatedItem.getQuantity() != null) {
                existingItem.setQuantity(updatedItem.getQuantity());
            }
            if (updatedItem.getPrice() != null) {
                existingItem.setPrice(updatedItem.getPrice());
            }

            InventoryItem updatedItemFromRepo = inventoryItemRepository.save(existingItem);
            log.info("Inventory item successfully updated: {}", updatedItemFromRepo.getProductCode());
            return mapper.toDTO(updatedItemFromRepo);
        } catch (Exception e) {
            log.error("Error occurred while updating inventory item with product code: {}", productCode, e);
            throw new RuntimeException("Error occurred while updating the inventory item", e);
        }
    }

    @Override
    @Cacheable(value = "inventoryItems", key = "#productCode", unless = "#result == null")
    public List<InventoryItemDTO> getInventoryItemByProductCode(String productCode, InventoryItemDTO inventoryItemDTO) {
        log.info("Fetching inventory item by product code: {}", productCode);
        try {
            Optional<InventoryItem> inventoryItems = inventoryItemRepository.findByProductCode(productCode);
            if (inventoryItems.isEmpty()) {
                log.error("Product with product code {} not found", productCode);
                throw new RuntimeException("The product not found: " + productCode);
            }
            return inventoryItems.stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching inventory item by product code: {}", productCode, e);
            throw new RuntimeException("Error occurred while fetching the inventory item", e);
        }
    }


    @Override
    @Cacheable(value = "inventoryItems", key = "#category", unless = "#result.isEmpty()")
    public List<InventoryItemDTO> getItemsByCategory(String category) {
        log.info("Fetching inventory items by category: {}", category);
        try {
            return inventoryItemRepository.findByCategory(category).stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching inventory items by category: {}", category, e);
            throw new RuntimeException("Error occurred while fetching inventory items by category", e);
        }
    }

    @Override
    public List<InventoryItemDTO> getActiveItems() {
        log.info("Fetching active inventory items");
        try {
            return inventoryItemRepository.findByIsActiveTrue()
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching active inventory items", e);
            throw new RuntimeException("Error occurred while fetching active inventory items", e);
        }
    }

    @Override
    public List<InventoryItemDTO> getInactiveItems() {
        log.info("Fetching inactive inventory items");
        try {
            return inventoryItemRepository.findByIsActiveFalse()
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching inactive inventory items", e);
            throw new RuntimeException("Error occurred while fetching inactive inventory items", e);
        }
    }

    @Override
    @Cacheable(value = "inventoryItems", key = "#minPrice + '-' + #maxPrice", unless = "#result.isEmpty()")
    public List<InventoryItemDTO> getItemsByPriceRange(Double minPrice, Double maxPrice) {
        log.info("Fetching inventory items in price range: {} - {}", minPrice, maxPrice);
        try {
            return inventoryItemRepository.findByPriceBetween(minPrice, maxPrice)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching inventory items by price range", e);
            throw new RuntimeException("Error occurred while fetching inventory items by price range", e);
        }
    }

    @Override
    public List<InventoryItemDTO> getItemsBySupplier(String supplier) {
        log.info("Fetching inventory items by supplier: {}", supplier);
        try {
            return inventoryItemRepository.findBySupplier(supplier)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching inventory items by supplier: {}", supplier, e);
            throw new RuntimeException("Error occurred while fetching inventory items by supplier", e);
        }
    }

    @Override
    public List<InventoryItemDTO> getItemsByProductNameContaining(String productName) {
        log.info("Fetching inventory items by product name containing: {}", productName);
        try {
            return inventoryItemRepository.findByProductNameContainingIgnoreCase(productName)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching inventory items by product name containing: {}", productName, e);
            throw new RuntimeException("Error occurred while fetching inventory items by product name", e);
        }
    }

    @Override
    public Page<InventoryItemDTO> getPaginatedInventoryItems(Pageable pageable) {
        log.info("Fetching paginated inventory items");
        try {
            Page<InventoryItem> inventoryItemsPage = inventoryItemRepository.findAll(pageable);
            return inventoryItemsPage.map(mapper::toDTO);
        } catch (Exception e) {
            log.error("Error occurred while fetching paginated inventory items", e);
            throw new RuntimeException("Error occurred while fetching paginated inventory items", e);
        }
    }
}
