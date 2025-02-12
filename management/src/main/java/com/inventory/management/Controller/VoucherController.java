package com.inventory.management.Controller;

import com.inventory.management.Dtos.VoucherDTO;
import com.inventory.management.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
@Slf4j
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping
    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
        log.info("Fetching all vouchers");
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    @GetMapping("/{code}")
    public ResponseEntity<VoucherDTO> getVoucherByCode(@PathVariable String code) {
        log.info("Fetching voucher with code: {}", code);
        return ResponseEntity.ok(voucherService.getByVoucherCode(code));
    }

    @PostMapping
    public ResponseEntity<VoucherDTO> createVoucher(@Valid @RequestBody VoucherDTO voucherDTO) {
        log.info("Creating a new voucher");
        VoucherDTO createdVoucher = voucherService.createVoucher(voucherDTO);
        return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable Long id, @Valid @RequestBody VoucherDTO voucherDTO) {
        log.info("Updating voucher with id: {}", id);
        return ResponseEntity.ok(voucherService.updateVoucher(id, voucherDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        log.info("Deleting voucher with id: {}", id);
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }
}