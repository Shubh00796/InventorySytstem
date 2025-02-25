package com.inventory.management.Dtos;

import com.inventory.management.Enums.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimDTO {
    private Long id;
    private String claimantName;
    private String policyNumber;
    private String hospitalName;
    private String diagnosis;
    private String treatmentDetails;
    private BigDecimal claimAmount;
    private BigDecimal approvedAmount;
    private ClaimStatus claimStatus;
    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private boolean cashless;
    private String insurerRemarks;
}