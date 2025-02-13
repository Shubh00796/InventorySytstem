package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.PropertyListingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.PropertyListingMapper;
import com.inventory.management.Model.PropertyListing;
import com.inventory.management.ReposiotryServices.PropertyReposiotryService;
import com.inventory.management.ValidatorLogics.PropertyListingPayloadProcessor;
import com.inventory.management.service.PropertyListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyListingServiceImpl implements PropertyListingService {
    private final PropertyReposiotryService reposiotryService;
    private final PropertyListingMapper mapper;
    private final PropertyListingPayloadProcessor payloadProcessor;

    @Override
    public List<PropertyListingDTO> getAllListings() {
        return reposiotryService.getAllProperties()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<PropertyListingDTO> getListingById(Long id) {
        return Optional.ofNullable(reposiotryService.getPropertyById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Given id didnt found" + id)));
    }

    @Override
    public PropertyListingDTO createListing(String source, String payload) {
        PropertyListing process = payloadProcessor.process(source, payload);
        PropertyListing propertyListing = reposiotryService.savePropertyListing(process);
        return mapper.toDTO(propertyListing);
    }

    @Override
    public PropertyListingDTO updateListing(Long id, PropertyListingDTO listingDTO) {
        PropertyListing propertyListing = reposiotryService.upPropertyListing(id, listingDTO);
        return mapper.toDTO(propertyListing);

    }

    @Override
    public void deleteListing(Long id) {
        reposiotryService.deletePropertyListing(id);

    }
}
