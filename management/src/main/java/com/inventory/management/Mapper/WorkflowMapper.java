package com.inventory.management.Mapper;

import com.inventory.management.Dtos.IssueDTO;
import com.inventory.management.Dtos.WorkflowDTO;
import com.inventory.management.Enums.IssueStatus;
import com.inventory.management.Enums.WorkflowStatus;
import com.inventory.management.Model.Issue;
import com.inventory.management.Model.Workflow;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkflowMapper {

    @Mapping(target = "status", source = "status")
    WorkflowDTO toDto(Workflow workflow);

    @Mapping(target = "status", source = "status")
    Workflow toEntity(WorkflowDTO workflowDTO);

    // Update existing entity from DTO (ignoring nulls)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "projectId", ignore = true)  // Prevent updating projectId
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(WorkflowDTO workflowDTO, @MappingTarget Workflow workflow);


    // Custom mapping methods for IssueStatus enum
    default String map(WorkflowStatus status) {
        return status != null ? status.name() : null;
    }

    default WorkflowStatus map(String status) {
        return status != null ? WorkflowStatus.valueOf(status) : null;
    }
}
