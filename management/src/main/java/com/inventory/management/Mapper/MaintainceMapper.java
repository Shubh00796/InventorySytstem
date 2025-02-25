package com.inventory.management.Mapper;

import com.inventory.management.Dtos.ClaimDTO;
import com.inventory.management.Dtos.MaintenanceRequestDto;
import com.inventory.management.Model.Claim;
import com.inventory.management.Model.MaintenanceRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MaintainceMapper {

    MaintenanceRequestDto toDto(MaintenanceRequest maintenanceRequest);

    MaintenanceRequest toEntity(MaintenanceRequestDto maintenanceRequestDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(MaintenanceRequestDto maintenanceRequestDto, @MappingTarget MaintenanceRequest maintenanceRequest);
}

