package com.inventory.management.Dtos;

import com.inventory.management.Enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime scheduledTime;
    private String recurrence;
    private TaskStatus status;
    private Set<Long> dependencyIds;
}
