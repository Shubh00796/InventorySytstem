package com.inventory.management.Mapper;

import com.inventory.management.Dtos.ActivityDataDTO;
import com.inventory.management.Model.ActivityData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityDataMapper {
    ActivityDataDTO toDTO(ActivityData activityData);
    ActivityData toEntity(ActivityDataDTO activityDataDTO);
}