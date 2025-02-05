package com.inventory.management.Repo;

import com.inventory.management.Model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    Page<InventoryItem> findAll(Pageable pageable);

    Optional<InventoryItem> findByProductCode(String productCode);

    List<InventoryItem> findByCategory(String category);

    List<InventoryItem> findByIsActiveTrue();

    List<InventoryItem> findByIsActiveFalse();

    List<InventoryItem> findByPriceBetween(Double minPrice, Double maxPrice);

    List<InventoryItem> findBySupplier(String supplier);

    List<InventoryItem> findByProductNameContainingIgnoreCase(String productName);


}
