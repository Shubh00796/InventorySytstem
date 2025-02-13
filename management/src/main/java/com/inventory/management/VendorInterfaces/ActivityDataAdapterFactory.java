package com.inventory.management.VendorInterfaces;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.VendorAdapators.FitbitActivityDataAdapter;
import com.inventory.management.VendorAdapators.GarminActivityDataAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityDataAdapterFactory {
    private final FitbitActivityDataAdapter fitbitAdapter;
    private final GarminActivityDataAdapter garminAdapter;

    public ActivityDataAdapter<?> getAdapter(String source) {
        switch (source.toUpperCase()) {
            case "FITBIT":
                return fitbitAdapter;
            case "GARMIN":
                return garminAdapter;
            default:
                throw new ResourceNotFoundException("Unsupported source: " + source);
        }
    }
}
