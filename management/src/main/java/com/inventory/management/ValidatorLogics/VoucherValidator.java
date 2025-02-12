package com.inventory.management.ValidatorLogics;

import com.inventory.management.Dtos.VoucherDTO;

public class VoucherValidator {

    public static void validatePercentageVoucher(VoucherDTO voucherDTO) {
        if (voucherDTO.getDiscountValue() == null ||
                voucherDTO.getDiscountValue() <= 0 ||
                voucherDTO.getDiscountValue() > 100) {
            throw new RuntimeException("Percentage voucher discount must be > 0 and <= 100");
        }
    }

    public static void validateFixedVoucher(VoucherDTO voucherDTO) {
        if (voucherDTO.getDiscountValue() == null ||
                voucherDTO.getDiscountValue() <= 0) {
            throw new RuntimeException("Fixed voucher discount must be a positive amount");
        }
    }
}