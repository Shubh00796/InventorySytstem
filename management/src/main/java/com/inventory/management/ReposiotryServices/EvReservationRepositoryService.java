package com.inventory.management.ReposiotryServices;


import com.inventory.management.Dtos.EVReservationDTO;
import com.inventory.management.Enums.EVReservationStatus;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.EVReservation;
import com.inventory.management.Repo.EVReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvReservationRepositoryService {

    private final EVReservationRepository repository;

    public List<EVReservation> getAllReservations() {
        return repository.findAll();
    }

    public List<EVReservation> getReservationsByStation(Long stationId) {
        return repository.findByStationId(stationId);
    }

    public List<EVReservation> getReservationsByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public EVReservation createReservation(EVReservation evReservation) {
        validateReservationRequest(evReservation);
        evReservation.setStatus(EVReservationStatus.REQUESTED);
        evReservation.setCreatedAt(LocalDateTime.now());

        return repository.save(evReservation);
    }

    public EVReservation updateReservation(Long id, EVReservationDTO evReservationDTO) {
        EVReservation reservation = findReservationById(id);

        updateReservationFields(reservation, evReservationDTO);
        reservation.setUpdatedAt(LocalDateTime.now());

        return repository.save(reservation);
    }

    public EVReservation confirmReservation(Long id) {
        EVReservation reservation = findReservationById(id);

        if (!EVReservationStatus.REQUESTED.equals(reservation.getStatus())) {
            throw new IllegalStateException("Only requested reservations can be confirmed.");
        }

        reservation.setStatus(EVReservationStatus.CONFIRMED);
        reservation.setUpdatedAt(LocalDateTime.now());

        return repository.save(reservation);
    }

    public EVReservation cancelReservation(Long id) {
        EVReservation reservation = findReservationById(id);

        if (reservation.getStatus() == EVReservationStatus.CANCELLED ||
                reservation.getStatus() == EVReservationStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed or already cancelled reservation.");
        }

        reservation.setStatus(EVReservationStatus.CANCELLED);
        reservation.setUpdatedAt(LocalDateTime.now());

        return repository.save(reservation);
    }

    public void deleteReservation(Long id) {
        EVReservation reservation = findReservationById(id);
        repository.delete(reservation);
    }

    private EVReservation findReservationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found for ID: " + id));
    }

    private void updateReservationFields(EVReservation reservation, EVReservationDTO dto) {
        if (dto.getStationId() != null) {
            reservation.setStationId(dto.getStationId());
        }
        if (dto.getUserId() != null) {
            reservation.setUserId(dto.getUserId());
        }
    }

    private void validateReservationRequest(EVReservation reservation) {
        if (reservation.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Scheduled time must be in the future.");
        }
        if (reservation.getDurationMinutes() < 15) {
            throw new IllegalArgumentException("Duration must be at least 15 minutes.");
        }
    }
}
