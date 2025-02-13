package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.PropertyListingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.PropertyListing;
import com.inventory.management.Repo.PropertyListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PropertyReposiotryService {
    private final PropertyListingRepository repository;

    public List<PropertyListing> getAllProperties() {
        return repository.findAll();
    }

    public Optional<PropertyListing> getPropertyById(Long id) {
        return repository.findById(id);
    }

    public PropertyListing savePropertyListing(PropertyListing propertyListing) {
        return repository.save(propertyListing);
    }

    public PropertyListing upPropertyListing(Long id, PropertyListingDTO propertyListingDTO) {
        PropertyListing propertyListing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Given id not found" + id));

        Optional.ofNullable(propertyListingDTO.getAddress()).ifPresent(propertyListing::setAddress);
        Optional.ofNullable(propertyListingDTO.getTitle()).ifPresent(propertyListing::setTitle);
        Optional.ofNullable(propertyListingDTO.getDescription()).ifPresent(propertyListing::setDescription);
        Optional.ofNullable(propertyListingDTO.getPrice()).ifPresent(propertyListing::setPrice);

        return repository.save(propertyListing);
    }

    public void deletePropertyListing(Long id) {
        PropertyListing propertyListing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Given id not found" + id));
        repository.delete(propertyListing);
    }
}
