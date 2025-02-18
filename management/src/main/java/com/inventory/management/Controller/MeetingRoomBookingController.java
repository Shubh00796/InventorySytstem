package com.inventory.management.Controller;

import com.inventory.management.Dtos.MeetingRoomBookingDTO;
import com.inventory.management.service.MeetingRoomBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingRoomBookingController {

    private final MeetingRoomBookingService meetingRoomBookingService;

    @PostMapping
    public ResponseEntity<MeetingRoomBookingDTO> createBooking(@RequestBody MeetingRoomBookingDTO request) {
        return ResponseEntity.ok(meetingRoomBookingService.createBooking(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingRoomBookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingRoomBookingService.getBookingById(id));
    }

    @GetMapping
    public ResponseEntity<Page<MeetingRoomBookingDTO>> getAllBookings(
            @RequestParam(required = false) Long roomId,
            Pageable pageable) {
        return ResponseEntity.ok(meetingRoomBookingService.getAllBookings(roomId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingRoomBookingDTO> updateBooking(@PathVariable Long id, @RequestBody MeetingRoomBookingDTO request) {
        return ResponseEntity.ok(meetingRoomBookingService.updateBooking(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        meetingRoomBookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
