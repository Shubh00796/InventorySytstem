package com.inventory.management.service;

import com.inventory.management.Dtos.PropertyListingDTO;
import com.inventory.management.Model.PropertyListing;

import java.util.List;
import java.util.Optional;

public interface PropertyListingService {

    List<PropertyListingDTO> getAllListings();
    Optional<PropertyListingDTO> getListingById(Long id);
    PropertyListingDTO createListing(String source ,String payload);
    PropertyListingDTO updateListing(Long id, PropertyListingDTO listingDTO);
    void deleteListing(Long id);
}
