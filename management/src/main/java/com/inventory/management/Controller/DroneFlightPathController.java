package com.inventory.management.Controller;

import com.inventory.management.Dtos.DroneFlightPathDTO;
import com.inventory.management.HelperClasses.OptimizationDeferredResultHolder;
import com.inventory.management.ServiceImpl.DroneFlightPathSeriveImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/api/flight-paths")
@RequiredArgsConstructor
public class DroneFlightPathController {

    private final DroneFlightPathSeriveImpl droneFlightPathService;
    private final OptimizationDeferredResultHolder deferredResultHolder;

    /**
     * Create a new flight path asynchronously
     */
    @PostMapping
    public DeferredResult<ResponseEntity<DroneFlightPathDTO>> createFlightPath(@RequestBody DroneFlightPathDTO flightPathDTO) {
        DeferredResult<ResponseEntity<DroneFlightPathDTO>> deferredResult = new DeferredResult<>();

        // Process the request asynchronously
        new Thread(() -> {
            DroneFlightPathDTO response = droneFlightPathService.createFlightPath(flightPathDTO);
            deferredResult.setResult(ResponseEntity.ok(response));
        }).start();

        return deferredResult;
    }

    /**
     * Get a flight path by ID asynchronously
     */
    @GetMapping("/{flightId}")
    public DeferredResult<ResponseEntity<DroneFlightPathDTO>> getFlightPath(@PathVariable Long flightId) {
        DeferredResult<ResponseEntity<DroneFlightPathDTO>> deferredResult = new DeferredResult<>();

        new Thread(() -> {
            DroneFlightPathDTO response = droneFlightPathService.getFlightPathById(flightId);
            deferredResult.setResult(ResponseEntity.ok(response));
        }).start();

        return deferredResult;
    }

        /**
         * Get all flight paths asynchronously
         */
    @GetMapping
    public DeferredResult<ResponseEntity<List<DroneFlightPathDTO>>> getAllFlightPaths() {
        DeferredResult<ResponseEntity<List<DroneFlightPathDTO>>> deferredResult = new DeferredResult<>();

        new Thread(() -> {
            List<DroneFlightPathDTO> response = droneFlightPathService.getAllFlightPaths();
            deferredResult.setResult(ResponseEntity.ok(response));
        }).start();

        return deferredResult;
    }

    /**
     * Update a flight path asynchronously
     */
    @PutMapping("/{flightId}")
    public DeferredResult<ResponseEntity<DroneFlightPathDTO>> updateFlightPath(
            @PathVariable Long flightId,
            @RequestBody DroneFlightPathDTO flightPathDTO) {

        DeferredResult<ResponseEntity<DroneFlightPathDTO>> deferredResult = new DeferredResult<>();

        new Thread(() -> {
            DroneFlightPathDTO response = droneFlightPathService.updateFlightPath(flightId, flightPathDTO);
            deferredResult.setResult(ResponseEntity.ok(response));
        }).start();

        return deferredResult;
    }

    /**
     * Delete a flight path asynchronously
     */
    @DeleteMapping("/{flightId}")
    public DeferredResult<ResponseEntity<String>> deleteFlightPath(@PathVariable Long flightId) {
        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();

        new Thread(() -> {
            droneFlightPathService.deleteFlightPath(flightId);
            deferredResult.setResult(ResponseEntity.ok("Flight path deleted successfully"));
        }).start();

        return deferredResult;
    }
}
