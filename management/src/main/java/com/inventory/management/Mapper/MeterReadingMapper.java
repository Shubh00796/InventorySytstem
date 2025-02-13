package com.inventory.management.Mapper;

import com.inventory.management.Dtos.MeterReadingDTO;
import com.inventory.management.Model.MeterReading;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterReadingMapper {
    MeterReadingDTO toDTO(MeterReading meterReading);
    MeterReading toEntity(MeterReadingDTO meterReadingDTO);
}