package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.Dtos.VoucherDTO;
import com.inventory.management.FactoryInterfaces.VoucherFactory;
import com.inventory.management.Model.Voucher;
import com.inventory.management.ValidatorLogics.VoucherValidator;
import org.springframework.stereotype.Component;

@Component
public class FixedVoucherFactory implements VoucherFactory {

    @Override
    public Voucher createVoucher(VoucherDTO voucherDTO) {
        VoucherValidator.validateFixedVoucher(voucherDTO);
        return getFixedVoucher(voucherDTO);
    }

    private static Voucher getFixedVoucher(VoucherDTO voucherDTO) {
        return Voucher.builder()
                .voucherType("FIXED")
                .discountValue(voucherDTO.getDiscountValue())
                .expirationDate(voucherDTO.getExpirationDate())
                .description(voucherDTO.getDescription())
                .code(voucherDTO.getCode())
                .build();
    }
}
