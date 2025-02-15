package com.inventory.management.Controller;

import com.inventory.management.Dtos.RentalBookingDTO;
import com.inventory.management.service.RentalBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalBookingController {
    private final RentalBookingService rentalBookingService;

    @GetMapping("/{id}")
    public ResponseEntity<RentalBookingDTO> getRentalBooking(@PathVariable Long id) {
        Optional<RentalBookingDTO> rental = rentalBookingService.getRentalBooking(id);
        return rental.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<RentalBookingDTO>> getRentalBookings(
            @RequestBody RentalBookingDTO filter, Pageable pageable) {
        return ResponseEntity.ok(rentalBookingService.getRentalBookings(
                filter.getStatus(), filter.getEquipmentId(), filter.getRenterId(), pageable));
    }

    @PostMapping
    public ResponseEntity<RentalBookingDTO> createRentalBooking(@RequestBody RentalBookingDTO request) {
        return ResponseEntity.ok(rentalBookingService.createRentalBooking(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalBookingDTO> updateRentalBooking(
            @PathVariable Long id, @RequestBody RentalBookingDTO request) {
        return ResponseEntity.ok(rentalBookingService.updateRentalBooking(id, request));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalBooking(@PathVariable Long id) {
        rentalBookingService.deleteRentalBooking(id);
        return ResponseEntity.noContent().build();
    }
}
