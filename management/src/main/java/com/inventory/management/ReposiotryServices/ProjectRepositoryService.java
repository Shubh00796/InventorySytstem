package com.inventory.management.ReposiotryServices;

import com.inventory.management.Model.Project;
import com.inventory.management.Repo.ProjectRepository;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectRepositoryService {

    private final ProjectRepository projectRepository;

    public Optional<Project> retrieveProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + id + " not found"));
    }

    boolean existsById(Long id) {
        return projectRepository.existsById(id);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = getProjectById(id);
        projectRepository.delete(project);
    }
}
