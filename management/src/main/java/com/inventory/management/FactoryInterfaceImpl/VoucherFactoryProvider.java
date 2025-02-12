package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.VoucherFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VoucherFactoryProvider {

    private final Map<String, VoucherFactory> voucherFactoryMap;

    @Autowired
    public VoucherFactoryProvider(PercentageVoucherFactory percentageVoucherFactory,
                                  FixedVoucherFactory fixedVoucherFactory) {
        this.voucherFactoryMap = Map.of(
                "PERCENTAGE", percentageVoucherFactory,
                "FIXED", fixedVoucherFactory
        );
    }

    public VoucherFactory getFactory(String voucherType) {
        return getVoucherFactory(voucherType);
    }

    private VoucherFactory getVoucherFactory(String voucherType) {
        VoucherFactory factory = voucherFactoryMap.get(voucherType.toUpperCase());
        if (factory == null) {
            throw new RuntimeException("Unsupported voucher type: " + voucherType);
        }
        return factory;
    }
}