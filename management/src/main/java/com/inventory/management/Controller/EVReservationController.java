package com.inventory.management.Controller;

import com.inventory.management.Dtos.EVReservationDTO;
import com.inventory.management.service.EVReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Slf4j
public class EVReservationController {

    private final EVReservationService reservationService;



    @GetMapping("/all")
    public ResponseEntity<List<EVReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping
    public ResponseEntity<EVReservationDTO> create(@RequestBody EVReservationDTO request) {
        log.info("Creating reservation - Station ID: {}, User ID: {}", request.getStationId(), request.getUserId());
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EVReservationDTO> update(@PathVariable Long id, @RequestBody EVReservationDTO request) {
        log.info("Updating reservation - ID: {}", id);
        return ResponseEntity.ok(reservationService.updateReservation(id, request));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<EVReservationDTO> confirm(@PathVariable Long id) {
        log.info("Confirming reservation - ID: {}", id);
        return ResponseEntity.ok(reservationService.confirmReservation(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<EVReservationDTO> cancel(@PathVariable Long id) {
        log.info("Cancelling reservation - ID: {}", id);
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting reservation - ID: {}", id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
