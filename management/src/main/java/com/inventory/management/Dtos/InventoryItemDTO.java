package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InventoryItemDTO {

    private Long id;
    private String productCode;
    private Integer quantity;
    private String productName;
    private String productDescription;
    private Double price;
    private String supplier;
    private String category;
    private Boolean isActive;
    private Integer version;

}