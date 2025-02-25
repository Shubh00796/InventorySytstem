package com.inventory.management.Repo;

import com.inventory.management.Model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Optional<Claim> findByPolicyNumber(String policyNumber);

}