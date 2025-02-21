package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.WorkflowDTO;
import com.inventory.management.Enums.WorkflowStatus;
import com.inventory.management.Mapper.WorkflowMapper;
import com.inventory.management.Model.Workflow;
import com.inventory.management.ReposiotryServices.WorkflowRepositoryService;
import com.inventory.management.events.WorkflowEventPublisher;
import com.inventory.management.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepositoryService repositoryService;
    private final WorkflowMapper mapper;
    private final WorkflowEventPublisher eventPublisher;

    @Override
    public WorkflowDTO createWorkflow(WorkflowDTO workflowDTO) {
        Workflow entity = mapper.toEntity(workflowDTO);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(Optional.ofNullable(entity.getStatus()).orElse(WorkflowStatus.PENDING));

        return saveAndConvertToDto(entity);
    }

    @Override
    public WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO) {
        Workflow existingWorkflow = getWorkflowEntity(id);
        validateStateTransition(existingWorkflow.getStatus(), workflowDTO.getStatus());

        mapper.updateEntityFromDto(workflowDTO, existingWorkflow);
        existingWorkflow.setUpdatedAt(LocalDateTime.now());

        return saveAndConvertToDto(existingWorkflow);
    }
    @Override
    public Optional<WorkflowDTO> getWorkflowById(Long id) {
        return Optional.ofNullable(repositoryService.getWorkFlowByIdFromDb(id))
                .map(mapper::toDto);
    }

    @Override
    public List<WorkflowDTO> getAllWorkflows() {
        return repositoryService.retriveAllWorkflows().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorkflow(Long id) {
        repositoryService.deleteProject(id);
    }

    private WorkflowDTO saveAndConvertToDto(Workflow workflow) {
        return mapper.toDto(repositoryService.saveWorkFlows(workflow));
    }

    private Workflow getWorkflowEntity(Long id) {
        return repositoryService.getWorkFlowByIdFromDb(id);
    }

    private void validateStateTransition(WorkflowStatus oldStatus, WorkflowStatus newStatus) {
        if (newStatus != null && !oldStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    String.format("Invalid state transition from %s to %s", oldStatus, newStatus)
            );
        }
    }
}
