package com.inventory.management.service;

import com.inventory.management.Dtos.MeetingRoomBookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetingRoomBookingService {

    MeetingRoomBookingDTO createBooking(MeetingRoomBookingDTO request);
    MeetingRoomBookingDTO getBookingById(Long id);
    Page<MeetingRoomBookingDTO> getAllBookings(Long roomId, Pageable pageable);
    MeetingRoomBookingDTO updateBooking(Long id, MeetingRoomBookingDTO request);
    void cancelBooking(Long id);
}
