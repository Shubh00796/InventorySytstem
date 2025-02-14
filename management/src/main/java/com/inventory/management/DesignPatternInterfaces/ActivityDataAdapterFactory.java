package com.inventory.management.DesignPatternInterfaces;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.DesignPatternsImpl.FitbitActivityDataAdapter;
import com.inventory.management.DesignPatternsImpl.GarminActivityDataAdapter;
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
