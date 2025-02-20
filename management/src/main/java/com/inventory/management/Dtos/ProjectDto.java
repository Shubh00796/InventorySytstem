package com.inventory.management.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {
    private Long id;
    @NotBlank(message = "Project name cannot be empty")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    private String name;
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    private List<String> settings;
    private List<String> roles;
    private List<String> permissions;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
