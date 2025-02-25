package com.inventory.management.Model;

import com.inventory.management.Enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String claimantName;
    private String policyNumber;
    private String hospitalName;
    private String diagnosis;
    private String treatmentDetails;

    private BigDecimal claimAmount;
    private BigDecimal approvedAmount;

    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;

    private boolean cashless;
    private String insurerRemarks;

}
