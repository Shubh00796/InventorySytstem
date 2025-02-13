package com.inventory.management.Dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FitbitActivityDTO {
    private String deviceId;
    private Integer stepCount;
    private String avgHeartRate;
    private String timeRecorded;
    private String batteryLevel;
}