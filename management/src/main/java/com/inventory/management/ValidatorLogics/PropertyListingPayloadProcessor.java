package com.inventory.management.ValidatorLogics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.management.Dtos.FitbitActivityDTO;
import com.inventory.management.Dtos.GarminActivityDTO;
import com.inventory.management.Dtos.SuburbanListingDTO;
import com.inventory.management.Dtos.UrbanListingDTO;
import com.inventory.management.Model.ActivityData;
import com.inventory.management.Model.PropertyListing;
import com.inventory.management.VendorInterfaces.ActivityDataAdapter;
import com.inventory.management.VendorInterfaces.ActivityDataAdapterFactory;
import com.inventory.management.VendorInterfaces.ListingAdapter;
import com.inventory.management.VendorInterfaces.PropertyListingAdapterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyListingPayloadProcessor {

    private final ObjectMapper objectMapper;
    private final PropertyListingAdapterFactory adapterFactory;

    public PropertyListing process(String source, String payload) {
        ListingAdapter<?> adapter = adapterFactory.getAdapter(source);
        Object dto = parsePayload(source, payload);
        PropertyListing propertyListing = adaptData(adapter, dto);
        propertyListing.setSource(source);
        return propertyListing;
    }

    private Object parsePayload(String source, String payload) {
        try {
            switch (source.toUpperCase()) {
                case "URBAN":
                    return objectMapper.readValue(payload, UrbanListingDTO.class);
                case "SUBURBAN":
                    return objectMapper.readValue(payload, SuburbanListingDTO.class);
                default:
                    throw new IllegalArgumentException("Unsupported vendor: " + source);
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid payload: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private PropertyListing adaptData(ListingAdapter<?> adapter, Object dto) {
        return ((ListingAdapter<Object>) adapter).convert(dto);
    }
}
