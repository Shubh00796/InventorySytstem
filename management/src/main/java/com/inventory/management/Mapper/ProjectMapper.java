package com.inventory.management.Mapper;

import com.inventory.management.Dtos.ProjectDto;
import com.inventory.management.Model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "settings", source = "settings", qualifiedByName = "listToString")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "listToString")
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "listToString")
    Project toEntity(ProjectDto projectDto);

    @Mapping(target = "settings", source = "settings", qualifiedByName = "stringToList")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "stringToList")
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "stringToList")
    ProjectDto toDto(Project project);

    // Partial update method (same conversions)
    @Mapping(target = "settings", source = "settings", qualifiedByName = "listToString")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "listToString")
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "listToString")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateProjectFromDto(ProjectDto projectDto, @MappingTarget Project project);

    // Helper: Convert List to String
    @Named("listToString")
    default String convertListToString(List<String> list) {
        return (list == null || list.isEmpty()) ? "" : String.join(",", list);
    }

    // Helper: Convert String to List
    @Named("stringToList")
    default List<String> convertStringToList(String str) {
        return (str == null || str.trim().isEmpty())
                ? Collections.emptyList()
                : List.of(str.split(","));
    }
}
