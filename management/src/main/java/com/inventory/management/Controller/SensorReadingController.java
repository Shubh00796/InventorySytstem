package com.inventory.management.Controller;

import com.inventory.management.Dtos.SensorReadingDTO;
import com.inventory.management.service.SensorReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensor-readings")
@RequiredArgsConstructor
public class SensorReadingController {

    private final SensorReadingService sensorReadingService;

    @GetMapping
    public ResponseEntity<List<SensorReadingDTO>> getAllSensorReadings() {
        return ResponseEntity.ok(sensorReadingService.getAllSensorReadings());
    }

    @GetMapping("/{sensorId}")
    public ResponseEntity<SensorReadingDTO> getBySensorId(@PathVariable String sensorId) {
        Optional<SensorReadingDTO> sensorReading = sensorReadingService.getBySensorId(sensorId);
        return sensorReading.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SensorReadingDTO> createSensorReading(@RequestBody SensorReadingDTO sensorReadingDTO) {
        return ResponseEntity.ok(sensorReadingService.createSensorReading(sensorReadingDTO));
    }

    @PutMapping("/{sensorId}")
    public ResponseEntity<SensorReadingDTO> updateSensorReading(
            @PathVariable String sensorId,
            @RequestBody SensorReadingDTO sensorReadingDTO) {
        return ResponseEntity.ok(sensorReadingService.updateSensorReading(sensorId, sensorReadingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensorReading(@PathVariable Long id) {
        sensorReadingService.deleteSensorReading(id);
        return ResponseEntity.noContent().build();
    }
}