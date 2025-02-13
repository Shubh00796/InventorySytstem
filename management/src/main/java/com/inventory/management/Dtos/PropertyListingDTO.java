package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyListingDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String address;
    private LocalDateTime listingDate;
    private String source;
}