package com.inventory.management.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(
        indexes = @Index(name = "idx_product_code", columnList = "productCode")
)
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String productCode;
    private Integer quantity;
    private String productName;
    private String productDescription;
    private Double price;
    private String supplier;
    private String category;
    private Boolean isActive;

    @Version
    private Integer version;

}
