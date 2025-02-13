package com.inventory.management.Controller;

import com.inventory.management.Dtos.ActivityDataDTO;
import com.inventory.management.service.ActivityDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-data")
@RequiredArgsConstructor
public class ActivityDataController {

    private final ActivityDataService activityDataService;

    @GetMapping
    public ResponseEntity<List<ActivityDataDTO>> getAllActivityData() {
        return ResponseEntity.ok(activityDataService.getAllActivityData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDataDTO> getActivityDataById(@PathVariable Long id) {
        return ResponseEntity.ok(activityDataService.getActivityDataById(id));
    }

    @PostMapping
    public ResponseEntity<ActivityDataDTO> createActivityData(
            @RequestParam String source,
            @RequestBody String payload) {
        return ResponseEntity.ok(activityDataService.createActivityData(source, payload));
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<ActivityDataDTO> updateActivityData(
            @PathVariable String deviceId,
            @RequestBody ActivityDataDTO activityDataDTO) {
        return ResponseEntity.ok(activityDataService.updateActivityData(deviceId, activityDataDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityData(@PathVariable Long id) {
        activityDataService.deleteActivityData(id);
        return ResponseEntity.noContent().build();
    }
}
