package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.MeetingRoomBookingDTO;
import com.inventory.management.Exceptions.BookingNotFoundException;
import com.inventory.management.Exceptions.OverlappingBookingException;
import com.inventory.management.Model.MeetingRoomBooking;
import com.inventory.management.Repo.MeetingRoomBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingBookingReposiotryService {

    private final MeetingRoomBookingRepository repository;


    public Page<MeetingRoomBooking> retriveAllBookingsFromDb(Specification<MeetingRoomBooking> spec, Pageable pageable) {
        return repository.findAll(pageable);

    }

    public Optional<MeetingRoomBooking> retriveBookingByIdFromDb(Long id) {
        return repository.findById(id);
    }

    public MeetingRoomBooking saveMeetingBooking(MeetingRoomBooking meetingRoomBooking) {
        return repository.save(meetingRoomBooking);
    }

    public MeetingRoomBooking updateMeetingBooking(Long id, MeetingRoomBookingDTO meetingRoomBookingDTO) {
        MeetingRoomBooking meetingRoomBooking = updateFieldsIfNotNull(id, meetingRoomBookingDTO);

        return repository.save(meetingRoomBooking);
    }

    public void  overlappingBooking(MeetingRoomBookingDTO meetingRoomBookingDTO){
        List<MeetingRoomBooking> overlapping = repository.findByRoomIdAndStartTimeLessThanAndEndTimeGreaterThan(meetingRoomBookingDTO.getRoomId(), meetingRoomBookingDTO.getEndTime(), meetingRoomBookingDTO.getStartTime());
        if(!overlapping.isEmpty()) {
            throw new OverlappingBookingException("Booking overlaps with an existing reservation");
        }
    }

    public void deleteMeetingBooking(Long id) {
        MeetingRoomBooking meetingRoomBooking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Id with Given booking not found" + id));
        repository.delete(meetingRoomBooking);

    }

    private MeetingRoomBooking updateFieldsIfNotNull(Long id, MeetingRoomBookingDTO meetingRoomBookingDTO) {
        MeetingRoomBooking meetingRoomBooking = checkIfIdIsNotNull(id);



        Optional.ofNullable(meetingRoomBookingDTO.getOrganizerName()).ifPresent(meetingRoomBooking::setOrganizerName);
        Optional.ofNullable(meetingRoomBookingDTO.getOrganizerEmail()).ifPresent(meetingRoomBooking::setOrganizerEmail);
        meetingRoomBooking.setStartTime(LocalDateTime.now());
        Optional.ofNullable(meetingRoomBookingDTO.getEndTime()).ifPresent(meetingRoomBooking::setEndTime);
        return meetingRoomBooking;
    }




    private MeetingRoomBooking checkIfIdIsNotNull(Long id) {
        MeetingRoomBooking meetingRoomBooking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Id with Given booking not found" + id));
        return meetingRoomBooking;
    }
}
