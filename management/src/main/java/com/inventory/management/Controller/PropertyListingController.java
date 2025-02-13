package com.inventory.management.Controller;

import com.inventory.management.Dtos.PropertyListingDTO;
import com.inventory.management.service.PropertyListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyListingController {

    private final PropertyListingService propertyListingService;

    @GetMapping
    public ResponseEntity<List<PropertyListingDTO>> getAllListings() {
        List<PropertyListingDTO> listings = propertyListingService.getAllListings();
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyListingDTO> getListingById(@PathVariable Long id) {
        Optional<PropertyListingDTO> listing = propertyListingService.getListingById(id);
        return listing.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PropertyListingDTO> createListing(
            @RequestParam String source,
            @RequestBody String payload) {
        PropertyListingDTO newListing = propertyListingService.createListing(source, payload);
        return ResponseEntity.ok(newListing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyListingDTO> updateListing(
            @PathVariable Long id,
            @RequestBody PropertyListingDTO listingDTO) {
        PropertyListingDTO updatedListing = propertyListingService.updateListing(id, listingDTO);
        return ResponseEntity.ok(updatedListing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        propertyListingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
