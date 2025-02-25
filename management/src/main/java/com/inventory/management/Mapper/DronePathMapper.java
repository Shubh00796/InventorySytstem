package com.inventory.management.Mapper;

import com.inventory.management.Dtos.ClaimDTO;
import com.inventory.management.Dtos.DroneFlightPathDTO;
import com.inventory.management.Model.Claim;
import com.inventory.management.Model.DroneFlightPath;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DronePathMapper {

    DroneFlightPathDTO toDto(DroneFlightPath flightPath);

    DroneFlightPath toEntity(DroneFlightPathDTO flightPathDTO);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DroneFlightPathDTO droneFlightPathDTO, @MappingTarget DroneFlightPath droneFlightPath);
}

