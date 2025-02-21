package com.inventory.management.Mapper;

import com.inventory.management.Dtos.IssueDTO;
import com.inventory.management.Enums.IssueStatus;
import com.inventory.management.Model.Issue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    @Mapping(target = "status", source = "status")
    IssueDTO toDto(Issue issue);

    @Mapping(target = "status", source = "status")
    Issue toEntity(IssueDTO issueDTO);

    // Update existing entity from DTO (ignoring nulls)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "projectId", ignore = true)  // Prevent updating projectId
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(IssueDTO issueDTO, @MappingTarget Issue issue);

    // List conversions
    List<IssueDTO> toDtoList(List<Issue> issues);
    List<Issue> toEntityList(List<IssueDTO> issueDTOs);

    // Custom mapping methods for IssueStatus enum
    default String map(IssueStatus status) {
        return status != null ? status.name() : null;
    }

    default IssueStatus map(String status) {
        return status != null ? IssueStatus.valueOf(status) : null;
    }
}
