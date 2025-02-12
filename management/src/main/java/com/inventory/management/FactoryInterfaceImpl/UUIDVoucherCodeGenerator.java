package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.VoucherCodeGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDVoucherCodeGenerator implements VoucherCodeGenerator {
    @Override
    public String generateCode() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
