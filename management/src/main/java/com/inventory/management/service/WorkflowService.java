package com.inventory.management.service;


import com.inventory.management.Dtos.WorkflowDTO;

import java.util.List;

public interface WorkflowService {

    WorkflowDTO createWorkflow(WorkflowDTO workflowDTO);

    WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO);

    WorkflowDTO getWorkflowById(Long id);

    List<WorkflowDTO> getAllWorkflows();

    void deleteWorkflow(Long id);
}
