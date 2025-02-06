package com.inventory.management.Controller;


import com.inventory.management.Dtos.InventoryItemDTO;
import com.inventory.management.service.InventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @PostMapping
    public ResponseEntity<InventoryItemDTO> addInventoryItem(@RequestBody InventoryItemDTO inventoryItemDTO) {
        try {
            InventoryItemDTO addedItem = inventoryItemService.addInventoryItem(inventoryItemDTO);
            return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{productCode}")
    public ResponseEntity<InventoryItemDTO> updateInventoryItem(@PathVariable String productCode, @RequestBody InventoryItemDTO inventoryItemDTO) {
        try {
            InventoryItemDTO updatedItem = inventoryItemService.updateInventoryItem(productCode, inventoryItemDTO);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/productCode/{productCode}")
    public ResponseEntity<List<InventoryItemDTO>> getInventoryItemByProductCode(@PathVariable String productCode) {
        List<InventoryItemDTO> items = inventoryItemService.getInventoryItemByProductCode(productCode);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<InventoryItemDTO>> getItemsByCategory(@PathVariable String category) {
        try {
            List<InventoryItemDTO> items = inventoryItemService.getItemsByCategory(category);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<InventoryItemDTO>> getActiveItems() {
        try {
            List<InventoryItemDTO> items = inventoryItemService.getActiveItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<InventoryItemDTO>> getInactiveItems() {
        try {
            List<InventoryItemDTO> items = inventoryItemService.getInactiveItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/priceRange")
    public ResponseEntity<List<InventoryItemDTO>> getItemsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        try {
            List<InventoryItemDTO> items = inventoryItemService.getItemsByPriceRange(minPrice, maxPrice);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/supplier/{supplier}")
    public ResponseEntity<List<InventoryItemDTO>> getItemsBySupplier(@PathVariable String supplier) {
        try {
            List<InventoryItemDTO> items = inventoryItemService.getItemsBySupplier(supplier);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/productName")
    public ResponseEntity<List<InventoryItemDTO>> getItemsByProductNameContaining(@RequestParam String productName) {
        try {
            List<InventoryItemDTO> items = inventoryItemService.getItemsByProductNameContaining(productName);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<InventoryItemDTO>> getPaginatedInventoryItems(@RequestParam int page, @RequestParam int size) {
        try {
            Page<InventoryItemDTO> pageResult = inventoryItemService.getPaginatedInventoryItems(PageRequest.of(page, size));
            return new ResponseEntity<>(pageResult.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
