package com.inventory.management.FactoryInterfaces;

import com.inventory.management.Dtos.VoucherDTO;
import com.inventory.management.Model.Voucher;

public interface VoucherFactory {
    Voucher createVoucher(VoucherDTO voucherDTO);
}