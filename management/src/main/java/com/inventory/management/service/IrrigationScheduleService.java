package com.inventory.management.service;

import com.inventory.management.Dtos.IrrigationScheduleDTO;

import java.util.List;

public interface IrrigationScheduleService {
    List<IrrigationScheduleDTO> getAllSchedules();

    List< IrrigationScheduleDTO> getScheduleById(Long id);

    IrrigationScheduleDTO createSchedule(IrrigationScheduleDTO scheduleDTO);

    IrrigationScheduleDTO updateSchedule(Long id, IrrigationScheduleDTO scheduleDTO);

    void deleteSchedule(Long id);


}
