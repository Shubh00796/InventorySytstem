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

        Workflow savedWorkflow = repositoryService.saveWorkFlows(entity);
        return mapper.toDto(savedWorkflow);

    }

    @Override
    public WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO) {
        Workflow existingWorkflow = repositoryService.getWorkFlowByIdFromDb(id);

        WorkflowStatus oldStatus = existingWorkflow.getStatus();
        WorkflowStatus newStatus = workflowDTO.getStatus();

        validateStateTransition(oldStatus, newStatus);

        mapper.updateEntityFromDto(workflowDTO, existingWorkflow);
        existingWorkflow.setUpdatedAt(LocalDateTime.now());


        return null;
    }

    @Override
    public WorkflowDTO getWorkflowById(Long id) {
        return null;
    }

    @Override
    public List<WorkflowDTO> getAllWorkflows() {
        return List.of();
    }

    @Override
    public void deleteWorkflow(Long id) {

    }

    private void validateStateTransition(WorkflowStatus oldStatus, WorkflowStatus newStatus) {
        if (newStatus != null && !oldStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    String.format("Invalid state transition from %s to %s", oldStatus, newStatus)
            );
        }
    }
}
