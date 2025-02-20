package com.inventory.management.Dtos;

import com.inventory.management.Enums.WorkflowStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDTO {

    private Long id;
    private String name;
    private String description;
    private WorkflowStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long projectId;

}
