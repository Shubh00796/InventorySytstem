package com.inventory.management.ValidatorLogics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.management.Dtos.FitbitActivityDTO;
import com.inventory.management.Dtos.GarminActivityDTO;
import com.inventory.management.Dtos.VendorBMeterReadingDTO;
import com.inventory.management.Model.ActivityData;
import com.inventory.management.VendorInterfaces.ActivityDataAdapter;
import com.inventory.management.VendorInterfaces.ActivityDataAdapterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivtyTrackingPayloadProcessor {

    private final ObjectMapper objectMapper;
    private final ActivityDataAdapterFactory adapterFactory;

    public ActivityData process(String source, String payload) {
        ActivityDataAdapter<?> adapter = adapterFactory.getAdapter(source);
        Object dto = parsePayload(source, payload);
        ActivityData activityData = adaptData(adapter, dto);
        activityData.setSource(source);
        return activityData;
    }

    private Object parsePayload(String source, String payload) {
        try {
            switch (source.toUpperCase()) {
                case "FITBIT":
                    return objectMapper.readValue(payload, FitbitActivityDTO.class);
                case "GARMIN":
                    return objectMapper.readValue(payload, GarminActivityDTO.class );
                default:
                    throw new IllegalArgumentException("Unsupported vendor: " + source);
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid payload: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private ActivityData adaptData(ActivityDataAdapter<?> adapter, Object dto) {
        return ((ActivityDataAdapter<Object>) adapter).adapt(dto);
    }
}
