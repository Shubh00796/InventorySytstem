package com.inventory.management.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "property_listings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime listingDate;

    @Column(nullable = false)
    private String source;
}