package com.inventory.management.Mapper;

import com.inventory.management.Dtos.CreateMaintenanceTaskRequest;
import com.inventory.management.Dtos.MaintenanceTaskDTO;
import com.inventory.management.Model.MaintenanceTask;
import com.inventory.management.Enums.MaintenanceTaskPriority;
import com.inventory.management.Enums.MaintenanceTaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {MaintenanceTaskStatus.class, MaintenanceTaskPriority.class})
public interface MaintenanceTaskMapper {
    @Mapping(target = "status", expression = "java(MaintenanceTaskStatus.valueOf(createRequest.getStatus().toUpperCase()))")
    @Mapping(target = "priority", expression = "java(MaintenanceTaskPriority.valueOf(createRequest.getPriority().toUpperCase()))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    MaintenanceTask toEntity(CreateMaintenanceTaskRequest createRequest);

    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    @Mapping(target = "priority", expression = "java(entity.getPriority().name())")
    MaintenanceTaskDTO toDTO(MaintenanceTask entity);
}