package com.inventory.management.Controller;

import com.inventory.management.Dtos.ClaimDTO;
import com.inventory.management.HelperClasses.DeferredResultHolder;
import com.inventory.management.Mapper.ClaimMapper;
import com.inventory.management.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
@Slf4j
public class ClaimController {
    private final ClaimService claimService;
    private final DeferredResultHolder deferredResultHolder;
    private final ClaimMapper mapper;

    @PostMapping
    public DeferredResult<ResponseEntity<ClaimDTO>> submitClaim(@Valid @RequestBody ClaimDTO claimDTO) {
        DeferredResult<ResponseEntity<ClaimDTO>> deferredResult = new DeferredResult<>(6000L);

        ClaimDTO claim = claimService.createClaim(claimDTO);
        deferredResultHolder.add(claim.getId(), deferredResult);
        deferredResult.setResult(ResponseEntity.ok(claim));

        deferredResult.onTimeout(() -> {
            deferredResultHolder.remove(claim.getId());
            deferredResult.setErrorResult(ResponseEntity.status(408).body(null));
        });
        return deferredResult;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimDTO> getClaimById(@PathVariable Long id) {
        log.info("Fetching claim with ID: {}", id);
        ClaimDTO claimDTO = claimService.getClaimById(id);
        return ResponseEntity.ok(claimDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClaimDTO>> getAllClaims() {
        log.info("Fetching all claims...");
        List<ClaimDTO> claims = claimService.getAllClaims();
        return ResponseEntity.ok(claims);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaimDTO> updateClaim(@PathVariable Long id, @Valid @RequestBody ClaimDTO claimDTO) {
        log.info("Updating claim with ID: {}", id);
        ClaimDTO updatedClaim = claimService.updateClaim(id, claimDTO);
        log.info("Claim updated successfully for ID: {}", id);
        return ResponseEntity.ok(updatedClaim);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        log.info("Deleting claim with ID: {}", id);
        claimService.deleteClaim(id);
        log.info("Claim deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
