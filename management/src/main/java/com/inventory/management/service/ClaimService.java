package com.inventory.management.service;

import com.inventory.management.Dtos.ClaimDTO;

import java.util.List;

public interface ClaimService {
    ClaimDTO createClaim(ClaimDTO claimDTO);
    ClaimDTO getClaimById(Long claimId);
    List<ClaimDTO> getAllClaims();
    ClaimDTO updateClaim(Long claimId, ClaimDTO claimDTO);
    void deleteClaim(Long claimId);
}