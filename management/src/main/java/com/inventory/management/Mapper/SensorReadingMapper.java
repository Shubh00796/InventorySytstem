package com.inventory.management.Mapper;

import com.inventory.management.Dtos.SensorReadingDTO;
import com.inventory.management.Model.SensorReading;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorReadingMapper {
    SensorReadingDTO toDTO(SensorReading sensorReading);
    SensorReading toEntity(SensorReadingDTO sensorReadingDTO);
}