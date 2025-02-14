package com.inventory.management.DesignPatternInterfaces;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.DesignPatternsImpl.SuburbanListingAdapter;
import com.inventory.management.DesignPatternsImpl.UrbanListingAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyListingAdapterFactory {
    private final UrbanListingAdapter urbanListingAdapter;
    private final SuburbanListingAdapter suburbanListingAdapter;

    public ListingAdapter<?> getAdapter(String source) {
        if ("URBAN".equalsIgnoreCase(source)) {
            return urbanListingAdapter;
        } else if ("SUBURBAN".equalsIgnoreCase(source)) {
            return suburbanListingAdapter;
        } else {
            throw new ResourceNotFoundException("Unsupported source: " + source);
        }
    }
}
