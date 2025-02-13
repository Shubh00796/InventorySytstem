package com.inventory.management.Mapper;

import com.inventory.management.Dtos.PropertyListingDTO;
import com.inventory.management.Model.PropertyListing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyListingMapper {
    PropertyListingDTO toDTO(PropertyListing listing);
    PropertyListing toEntity(PropertyListingDTO listingDTO);
}