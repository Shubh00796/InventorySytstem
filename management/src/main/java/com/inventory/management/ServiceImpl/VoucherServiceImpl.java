package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.VoucherDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.VoucherMapper;
import com.inventory.management.Model.Voucher;
import com.inventory.management.ReposiotryServices.VoucherRepositoryService;
import com.inventory.management.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepositoryService voucherRepositoryService;
    private final VoucherMapper voucherMapper;

    @Override
    public List<VoucherDTO> getAllVouchers() {
        List<Voucher> vouchers = voucherRepositoryService.getAllVouchers();
        return vouchers.stream()
                .map(voucherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VoucherDTO getByVoucherCode(String code) {
        Voucher voucher = voucherRepositoryService.getVoucherById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher with code " + code + " not found"));
        return voucherMapper.toDTO(voucher);
    }

    @Override
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        Voucher voucher = voucherMapper.toEntity(voucherDTO);
        Voucher savedVoucher = voucherRepositoryService.saveVoucher(voucher);
        return voucherMapper.toDTO(savedVoucher);
    }

    @Override
    public VoucherDTO updateVoucher(Long id, VoucherDTO voucherDTO) {
        Voucher updatedVoucher = voucherRepositoryService.updateVoucher(id.toString(), voucherDTO);
        return voucherMapper.toDTO(updatedVoucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        voucherRepositoryService.deleteVoucher(id);
    }
}
