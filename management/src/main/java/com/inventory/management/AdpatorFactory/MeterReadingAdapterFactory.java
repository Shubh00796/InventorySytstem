package com.inventory.management.AdpatorFactory;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.VendorAdapators.VendorAMeterReadingAdapter;
import com.inventory.management.VendorAdapators.VendorBMeterReadingAdapter;
import com.inventory.management.VendorInterfaces.MeterReadingAdapter;
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
