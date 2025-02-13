package com.inventory.management.ValidatorLogics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.management.AdpatorFactory.MeterReadingAdapterFactory;
import com.inventory.management.Dtos.VendorAMeterReadingDTO;
import com.inventory.management.Dtos.VendorBMeterReadingDTO;
import com.inventory.management.Model.MeterReading;
import com.inventory.management.VendorInterfaces.MeterReadingAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeterReadingPayloadProcessor {

    private final ObjectMapper objectMapper;
    private final MeterReadingAdapterFactory adapterFactory;

    public MeterReading process(String vendor, String payload) {
        MeterReadingAdapter<?> adapter = adapterFactory.getAdapter(vendor);
        try {
            MeterReading meterReading;
            if ("VENDOR_A".equalsIgnoreCase(vendor)) {
                VendorAMeterReadingDTO dto = objectMapper.readValue(payload, VendorAMeterReadingDTO.class);
                MeterReadingAdapter<VendorAMeterReadingDTO> vendorAAdapter = (MeterReadingAdapter<VendorAMeterReadingDTO>) adapter;
                meterReading = vendorAAdapter.adapt(dto);
            } else if ("VENDOR_B".equalsIgnoreCase(vendor)) {
                VendorBMeterReadingDTO dto = objectMapper.readValue(payload, VendorBMeterReadingDTO.class);
                MeterReadingAdapter<VendorBMeterReadingDTO> vendorBAdapter = (MeterReadingAdapter<VendorBMeterReadingDTO>) adapter;
                meterReading = vendorBAdapter.adapt(dto);
            } else {
                throw new IllegalArgumentException("Unsupported vendor: " + vendor);
            }
            meterReading.setVendor(vendor);  // ✅ Ensure correct vendor is set
            return meterReading;
        } catch (Exception e) {
            throw new RuntimeException("Invalid payload: " + e.getMessage(), e);
        }
    }
}