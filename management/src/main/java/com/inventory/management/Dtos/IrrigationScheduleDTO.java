package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrrigationScheduleDTO {
    private Long id;
    private String fieldName;
    private Double waterAmount;
    private LocalDateTime scheduleTime;
    private Boolean active;
}
