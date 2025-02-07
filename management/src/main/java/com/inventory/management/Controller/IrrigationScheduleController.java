package com.inventory.management.controller;

import com.inventory.management.Dtos.IrrigationScheduleDTO;
import com.inventory.management.service.IrrigationScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/irrigation-schedules")
@RequiredArgsConstructor
@Slf4j
public class IrrigationScheduleController {

    private final IrrigationScheduleService irrigationScheduleService;

    @GetMapping
    public ResponseEntity<List<IrrigationScheduleDTO>> getAllSchedules() {
        log.info("Fetching all irrigation schedules");
        List<IrrigationScheduleDTO> schedules = irrigationScheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<IrrigationScheduleDTO>> getScheduleById(@PathVariable Long id) {
        log.info("Fetching schedule with ID: {}", id);
        List<IrrigationScheduleDTO> schedule = irrigationScheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    public ResponseEntity<IrrigationScheduleDTO> createSchedule(@Valid @RequestBody IrrigationScheduleDTO scheduleDTO) {
        log.info("Creating new schedule");
        IrrigationScheduleDTO createdSchedule = irrigationScheduleService.createSchedule(scheduleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IrrigationScheduleDTO> updateSchedule(@PathVariable Long id,
                                                                @Valid @RequestBody IrrigationScheduleDTO scheduleDTO) {
        log.info("Updating schedule with ID: {}", id);
        IrrigationScheduleDTO updatedSchedule = irrigationScheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id) {
        log.info("Deleting schedule with ID: {}", id);
        irrigationScheduleService.deleteSchedule(id);
    }
}
