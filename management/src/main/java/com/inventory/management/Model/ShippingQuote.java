package com.inventory.management.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_quotes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private String shippingMethod;

    @Column(nullable = false)
    private String carrier;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}