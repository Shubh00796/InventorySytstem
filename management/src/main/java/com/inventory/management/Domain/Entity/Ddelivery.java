package com.inventory.management.Domain.Entity;

import com.inventory.management.Enums.PackageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


import org.hibernate.annotations.GenericGenerator;


@Entity
    @Table(name = "deliveries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ddelivery {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String sender;

    @Column(nullable = false, length = 100)
    private String recipient;

    @Column(nullable = false)
    private double shippingCost;

    @Column(nullable = false)
    private double packageWeight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageType packageType;

    @Column(nullable = false)
    private boolean insurance;

    @Column(nullable = false)
    private boolean isExpeditedShipping;

    @Column(nullable = false)
    private boolean fragileHandling;


}
