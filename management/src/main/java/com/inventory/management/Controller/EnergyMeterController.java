package com.inventory.management.Controller;

import com.inventory.management.Dtos.EnergyMeterDTO;
import com.inventory.management.service.EnergyMeterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/energy-meters")
@RequiredArgsConstructor
@Slf4j
public class EnergyMeterController {

    private final EnergyMeterService energyMeterService;


    @PostMapping
    public ResponseEntity<EnergyMeterDTO> createEnergyMeter(@RequestBody EnergyMeterDTO energyMeterDTO) {
        EnergyMeterDTO createdMeter = energyMeterService.createEnergyMeter(energyMeterDTO);
        return ResponseEntity.ok(createdMeter);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EnergyMeterDTO> updateEnergyMeter(
            @PathVariable Long id,
            @RequestBody EnergyMeterDTO energyMeterDTO) {
        EnergyMeterDTO updatedMeter = energyMeterService.updateEnergyMeter(id, energyMeterDTO);
        return ResponseEntity.ok(updatedMeter);
    }


    @PostMapping("/{id}/record-consumption")
    public ResponseEntity<EnergyMeterDTO> recordEnergyConsumption(
            @PathVariable Long id,
            @RequestParam double consumption) {
        EnergyMeterDTO updatedMeter = energyMeterService.recordEnergyConsumption(id, consumption);
        return ResponseEntity.ok(updatedMeter);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EnergyMeterDTO> getEnergyMeterById(@PathVariable Long id) {
        EnergyMeterDTO meter = energyMeterService.getEnergyMeterById(id);
        return ResponseEntity.ok(meter);
    }


    @GetMapping
    public ResponseEntity<List<EnergyMeterDTO>> getAllEnergyMeters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<EnergyMeterDTO> meters = energyMeterService.getAllEnergyMeters(page, size);
        return ResponseEntity.ok(meters);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnergyMeter(@PathVariable Long id) {
        energyMeterService.deleteEnergyMeter(id);
        return ResponseEntity.ok("Energy Meter with ID " + id + " deleted successfully.");
    }
}
