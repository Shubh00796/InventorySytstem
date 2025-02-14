package com.inventory.management.Domain.DomainController;

import com.inventory.management.Domain.DomainService.DdeliveryService;
import com.inventory.management.Domain.Entity.Ddelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ddeliveries")
@RequiredArgsConstructor
public class DdliveryControllers {

    private final DdeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<Ddelivery> createDelivery(@RequestBody Ddelivery ddelivery) {
        return ResponseEntity.ok(deliveryService.createDelivery(ddelivery));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ddelivery> getDelivery(@PathVariable UUID id) {
        return ResponseEntity.ok(deliveryService.getDelivery(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ddelivery> updateDelivery(@PathVariable UUID id, @RequestBody Ddelivery ddelivery) {
        return ResponseEntity.ok(deliveryService.updateDelivery(id, ddelivery));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable UUID id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Ddelivery>> listDeliveries() {
        return ResponseEntity.ok(deliveryService.listDeliveries());
    }
}
