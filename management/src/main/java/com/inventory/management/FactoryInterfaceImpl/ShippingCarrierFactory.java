package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.DesignPatternInterfaces.FedExCarrier;
import com.inventory.management.DesignPatternInterfaces.ShippingCarrier;
import com.inventory.management.DesignPatternsImpl.DHLCarrier;
import com.inventory.management.DesignPatternsImpl.UPSCarrier;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ShippingCarrierFactory {

    private final Map<String,   ShippingCarrier> carrierMap = new ConcurrentHashMap<>();
    private final FedExCarrier fedExCarrier;
    private final UPSCarrier upsCarrier;
    private final DHLCarrier dhlCarrier;

    @PostConstruct
    public void init() {
        carrierMap.put("FEDEX", fedExCarrier);
        carrierMap.put("UPS", upsCarrier);
        carrierMap.put("DHL", dhlCarrier);
    }

    public ShippingCarrier getShippingCarrier(String carrier) {
        ShippingCarrier shippingCarrier = carrierMap.get(carrier.toUpperCase());
        if (shippingCarrier == null) {
            throw new IllegalArgumentException("Unsupported carrier: " + carrier);
        }
        return shippingCarrier;
    }
}
