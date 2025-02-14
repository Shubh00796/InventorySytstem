package com.inventory.management.DesignPatternsImpl;

import com.inventory.management.Dtos.SuburbanListingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.PropertyListing;
import com.inventory.management.DesignPatternInterfaces.ListingAdapter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class SuburbanListingAdapter implements ListingAdapter<SuburbanListingDTO> {

    @Override
    public PropertyListing convert(SuburbanListingDTO source) {
        return PropertyListing.builder()
                .title(source.getPropertyTitle())
                .description(source.getDetails())
                .price(source.getListingPrice())
                .address(source.getFullAddress())
                .listingDate(parseDate(source.getListedOn()))
                .source("SUBURBAN")
                .build();
    }

    private LocalDateTime parseDate(Long timestamp) {
        if (timestamp == null || timestamp <= 0) {
            throw new ResourceNotFoundException("Valid listedOn timestamp is required.");
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }
}