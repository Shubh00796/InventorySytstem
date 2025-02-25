package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.ClaimNotFoundException;
import com.inventory.management.Model.Claim;
import com.inventory.management.Repo.ClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClaimReposiotryService {
    private final ClaimRepository claimRepository;


    public List<Claim> retriveAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> retriveClaimById(Long claimId) {
        return claimRepository.findById(claimId);
    }

    public Claim saveClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    public void deleteClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim with given Id not found" + claimId));

        claimRepository.delete(claim);
    }

    public Optional<Claim> retriveClaimByPolicyNumber(String policyNumber) {
        return claimRepository.findByPolicyNumber(policyNumber);
    }
}
