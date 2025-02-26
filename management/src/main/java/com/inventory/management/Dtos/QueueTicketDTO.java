package com.inventory.management.Dtos;

import com.inventory.management.Enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueTicketDTO {

    private Long id;
    private String customerName;
    private String serviceType;
    private LocalDateTime createdAt;
    private int estimatedWaitTime;
    private TicketStatus status;
    private String customerEmail;
    private String phoneNumber;
    private boolean isVip;
}