package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private String businessId;
    private String customerName;
    private String review;
    private int rating;
    private LocalDateTime createdAt;
}