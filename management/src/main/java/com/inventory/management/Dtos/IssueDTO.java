package com.inventory.management.Dtos;

import com.inventory.management.Enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueDTO {

    private Long id;
    private String title;
    private String description;
    private IssueStatus status;
    private String priority;
    private String assigneeId;
    private String reporterId;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
