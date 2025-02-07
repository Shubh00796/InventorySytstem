package com.inventory.management.Mapper;

import com.inventory.management.Dtos.IrrigationScheduleDTO;
import com.inventory.management.Model.IrrigationSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IrrigationScheduleMapper {
    IrrigationSchedule toEntity(IrrigationScheduleDTO dto);
    IrrigationScheduleDTO toDTO(IrrigationSchedule entity);
}