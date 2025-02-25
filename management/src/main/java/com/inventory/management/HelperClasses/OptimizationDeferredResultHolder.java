package com.inventory.management.HelperClasses;

import com.inventory.management.Dtos.ClaimDTO;
import com.inventory.management.Dtos.DroneFlightPathDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OptimizationDeferredResultHolder {

    private final Map<Long, DeferredResult<ResponseEntity<DroneFlightPathDTO>>> resultMap = new ConcurrentHashMap<>();

    public void add(Long flightId, DeferredResult<ResponseEntity<DroneFlightPathDTO>> result) {
        resultMap.put(flightId, result);
    }

    public Optional<DeferredResult<ResponseEntity<DroneFlightPathDTO>>> getId(Long flightId) {
        return Optional.ofNullable(resultMap.get(flightId));
    }

    public void remove(Long flightId) {
        resultMap.remove(flightId);
    }
}
