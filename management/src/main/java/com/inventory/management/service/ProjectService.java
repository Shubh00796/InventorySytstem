package com.inventory.management.service;

import com.inventory.management.Dtos.ProjectDto;

public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);
    ProjectDto updateProject(Long projectId, ProjectDto projectDto);
    ProjectDto getProjectById(Long projectId);
    void deleteProject(Long projectId);
}
