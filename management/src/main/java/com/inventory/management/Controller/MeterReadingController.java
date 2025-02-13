package com.inventory.management.Controller;

import com.inventory.management.Dtos.MeterReadingDTO;
import com.inventory.management.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meter-readings")
@RequiredArgsConstructor
public class MeterReadingController {

    private final MeterReadingService meterReadingService;

    @GetMapping
    public ResponseEntity<List<MeterReadingDTO>> getAllMeterReadings() {
        List<MeterReadingDTO> readings = meterReadingService.getAll();
        return ResponseEntity.ok(readings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeterReadingDTO> getMeterReadingById(@PathVariable Long id) {
        return meterReadingService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Meter reading not found for ID: " + id));
    }

    @PostMapping
    public ResponseEntity<MeterReadingDTO> createMeterReading(
            @RequestParam String vendor,
            @RequestBody String payload) {
        MeterReadingDTO createdReading = meterReadingService.create(vendor, payload);
        return ResponseEntity.ok(createdReading);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeterReadingDTO> updateMeterReading(@PathVariable Long id, @RequestBody MeterReadingDTO meterReadingDTO) {
        MeterReadingDTO updatedReading = meterReadingService.update(id, meterReadingDTO);
        return ResponseEntity.ok(updatedReading);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeterReading(@PathVariable Long id) {
        meterReadingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
