package com.inventory.management.service;

import com.inventory.management.Dtos.EVReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EVReservationService {
    List<EVReservationDTO> getReservations();


    EVReservationDTO createReservation(EVReservationDTO request);

    EVReservationDTO updateReservation(Long id, EVReservationDTO request);

    EVReservationDTO confirmReservation(Long id);

    EVReservationDTO cancelReservation(Long id);

    void deleteReservation(Long id);
}