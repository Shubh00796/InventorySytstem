package com.inventory.management.Dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuburbanListingDTO {
    private String propertyTitle;
    private String details;
    private Double listingPrice;
    private String fullAddress;
    private Long listedOn;
    private String realtorInfo;
}