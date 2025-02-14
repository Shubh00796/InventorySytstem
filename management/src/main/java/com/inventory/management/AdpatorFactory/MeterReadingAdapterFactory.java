package com.inventory.management.AdpatorFactory;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.DesignPatternsImpl.VendorAMeterReadingAdapter;
import com.inventory.management.DesignPatternsImpl.VendorBMeterReadingAdapter;
import com.inventory.management.DesignPatternInterfaces.MeterReadingAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeterReadingAdapterFactory {

    private final VendorAMeterReadingAdapter vendorAMeterReadingAdapter;
    private final VendorBMeterReadingAdapter vendorBMeterReadingAdapter;



    public MeterReadingAdapter<?> getAdapter(String vendor) {
        if ("VENDOR_A".equalsIgnoreCase(vendor)) {
            return vendorAMeterReadingAdapter;
        } else if ("VENDOR_B".equalsIgnoreCase(vendor)) {
            return vendorBMeterReadingAdapter;
        } else {
            throw new ResourceNotFoundException("Unsupported vendor: " + vendor);
        }
    }
}
