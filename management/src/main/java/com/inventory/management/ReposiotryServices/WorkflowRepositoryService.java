package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Issue;
import com.inventory.management.Model.Workflow;
import com.inventory.management.Repo.WorkflowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkflowRepositoryService {

    private final WorkflowRepository workflowRepository;
    private final ProjectRepositoryService projectRepositoryService;


    public Workflow getWorkFlowByIdFromDb(Long id) {
        return workflowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + id + " not found"));
    }
    public List<Workflow> retriveAllWorkflows() {
        return workflowRepository.findAll();
    }

    public Optional<Workflow> retrieveWorkflowByName(String name) {
        return workflowRepository.findByName(name);
    }

    public Workflow saveWorkFlows(Workflow workflow) {
        if (workflow.getProjectId() == null || !projectRepositoryService.existsById(workflow.getProjectId())) {
            throw new ResourceNotFoundException("Cannot create issue. Project with ID " + workflow.getProjectId() + " does not exist.");
        }

        return workflowRepository.save(workflow);
    }

    public void deleteProject(Long id) {
        Workflow workflow = getWorkFlowByIdFromDb(id);
        workflowRepository.delete(workflow);
    }
}
