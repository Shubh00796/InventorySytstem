package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.VoucherDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.FactoryInterfaces.VoucherCodeGenerator;
import com.inventory.management.Model.Voucher;
import com.inventory.management.Repo.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoucherRepositoryService {

    private final VoucherRepository voucherRepository;
    private final VoucherCodeGenerator voucherCodeGenerator;

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> getVoucherById(String code) {
        return voucherRepository.findByCode(code);
    }

    public Voucher saveVoucher(Voucher voucher) {
        voucher.setCode(voucherCodeGenerator.generateCode());
        return voucherRepository.save(voucher);
    }

    public Voucher updateVoucher(String code, VoucherDTO voucherDTO) {
        Voucher voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Code " + code + " not found"));

        Optional.ofNullable(voucherDTO.getVoucherType()).ifPresent(voucher::setVoucherType);
        Optional.ofNullable(voucherDTO.getDescription()).ifPresent(voucher::setDescription);
        Optional.of(voucherDTO.getDiscountValue()).ifPresent(voucher::setDiscountValue);

        return voucherRepository.save(voucher);
    }

    public void deleteVoucher(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor reading not found with id: " + id));

        voucherRepository.delete(voucher);

    }
}
