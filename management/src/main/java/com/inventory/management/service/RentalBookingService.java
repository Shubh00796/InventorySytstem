package com.inventory.management.service;

import com.inventory.management.Dtos.RentalBookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RentalBookingService {
    Optional<RentalBookingDTO> getRentalBooking(Long id);
    Page<RentalBookingDTO> getRentalBookings(String status, Long equipmentId, Long renterId, Pageable pageable);
    RentalBookingDTO createRentalBooking(RentalBookingDTO request);
    RentalBookingDTO updateRentalBooking(Long id, RentalBookingDTO request);
    void deleteRentalBooking(Long id);
}