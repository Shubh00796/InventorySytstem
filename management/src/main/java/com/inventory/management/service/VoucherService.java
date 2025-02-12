package com.inventory.management.service;

import com.inventory.management.Dtos.VoucherDTO;

import java.util.List;

public interface VoucherService {
    List<VoucherDTO> getAllVouchers();
    VoucherDTO getByVoucherCode(String code);
    VoucherDTO createVoucher(VoucherDTO voucherDTO);
    VoucherDTO updateVoucher(Long id, VoucherDTO voucherDTO);
    void deleteVoucher(Long id);
}