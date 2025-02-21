package com.inventory.management.Dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDTO {
    private Long id;
    private Long projectId;
    private String reportType;
    private String name;
    private String description;
    private String reportData;
    private LocalDateTime generatedAt;
}
