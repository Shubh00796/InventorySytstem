package com.inventory.management.Controller;

import com.inventory.management.Dtos.ProjectDto;
import com.inventory.management.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    // Create a new project
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
        log.info("Received request to create project: {}", projectDto);

        ProjectDto createdProject = projectService.createProject(projectDto);
        return ResponseEntity.created(URI.create("/api/v1/projects/" + createdProject.getId()))
                .body(createdProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        log.info("Received request to get project with ID: {}", id);

        ProjectDto projectDto = projectService.getProjectById(id);
        return ResponseEntity.ok(projectDto);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId, @RequestBody ProjectDto projectDTO) {
        ProjectDto updatedProject = projectService.updateProject(projectId, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.info("Received request to delete project with ID: {}", id);

        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
