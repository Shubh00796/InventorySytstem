package com.inventory.management.DesignPatternsImpl;

import com.inventory.management.Dtos.UrbanListingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.PropertyListing;
import com.inventory.management.DesignPatternInterfaces.ListingAdapter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Component
public class UrbanListingAdapter implements ListingAdapter<UrbanListingDTO> {

    @Override
    public PropertyListing convert(UrbanListingDTO source) {
        return PropertyListing.builder()
                .title(source.getHeadline())
                .description(source.getSummary())
                .price(source.getCost())
                .address(source.getLocation())
                .listingDate(parseDate(source.getDateListed()))
                .source("URBAN")
                .build();
    }

    private LocalDateTime parseDate(String date) {
        if (date == null || date.isBlank()) {
            throw new ResourceNotFoundException("Date listed is required for Urban listings.");
        }
        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            throw new ResourceNotFoundException("Invalid date format. Expected ISO-8601.");
        }
    }
}