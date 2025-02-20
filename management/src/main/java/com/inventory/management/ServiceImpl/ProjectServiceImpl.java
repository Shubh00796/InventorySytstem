package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.ProjectDto;
import com.inventory.management.Mapper.ProjectMapper;
import com.inventory.management.Model.Project;
import com.inventory.management.ReposiotryServices.ProjectRepositoryService;
import com.inventory.management.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepositoryService repositoryService;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        log.info("Creating new project: {}", projectDto);

        Project entity = projectMapper.toEntity(projectDto);
        entity.setCreatedAt(LocalDateTime.now());


        Project savedProject = repositoryService.saveProject(entity);

        log.info("Project created successfully with ID: {}", savedProject.getId());

        return projectMapper.toDto(savedProject);
    }

    @Override
    public ProjectDto updateProject(Long projectId, ProjectDto projectDto) {
        log.info("Updating project with ID: {}", projectId);

        Project existingProject = repositoryService.getProjectById(projectId);
        if (existingProject.getId() == null) {
            throw new IllegalStateException("Project ID cannot be null while updating.");
        }

        projectMapper.updateProjectFromDto(projectDto, existingProject);
        existingProject.setUpdatedAt(LocalDateTime.now());

        Project updatedProject = repositoryService.saveProject(existingProject);

        log.info("Project updated successfully with ID: {}", updatedProject.getId());

        return projectMapper.toDto(updatedProject);
    }

    @Override
    public ProjectDto getProjectById(Long projectId) {
        log.info("Fetching project with ID: {}", projectId);

        Project project = repositoryService.getProjectById(projectId);

        log.info("Project retrieved successfully with ID: {}", projectId);

        return projectMapper.toDto(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        log.info("Deleting project with ID: {}", projectId);

        repositoryService.deleteProject(projectId);

        log.info("Project deleted successfully with ID: {}", projectId);
    }
}
