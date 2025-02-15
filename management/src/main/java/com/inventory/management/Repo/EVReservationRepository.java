package com.inventory.management.Repo;

import com.inventory.management.Enums.EVReservationStatus;
import com.inventory.management.Model.EVReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EVReservationRepository extends JpaRepository<EVReservation, Long> {
    List<EVReservation> findByStatus(EVReservationStatus status);

    List<EVReservation> findByStationId(Long stationId);

    List<EVReservation> findByUserId(Long userId);

    Page<EVReservation> findByStatusAndStationId(EVReservationStatus status, Long stationId, Pageable pageable);

    Page<EVReservation> findByStatusAndUserId(EVReservationStatus status, Long userId, Pageable pageable);
}