package com.inventory.management.Mapper;

import com.inventory.management.Dtos.IssueDTO;
import com.inventory.management.Dtos.ReportDTO;
import com.inventory.management.Enums.IssueStatus;
import com.inventory.management.Enums.ReportType;
import com.inventory.management.Model.Issue;
import com.inventory.management.Model.Report;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    ReportDTO toDto(Report report);

    Report toEntity(ReportDTO reportDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generatedAt", ignore = true)
    @Mapping(target = "projectId", ignore = true)  // Prevent updating projectId
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReportDTO reportDTO, @MappingTarget Report report);



    // Custom mapping methods for IssueStatus enum
    default String map(ReportType status) {
        return status != null ? status.name() : null;
    }

    default ReportType map(String status) {
        return status != null ? ReportType.valueOf(status) : null;
    }

}
