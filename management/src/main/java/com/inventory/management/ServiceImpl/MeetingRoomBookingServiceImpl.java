package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.MeetingRoomBookingDTO;
import com.inventory.management.Exceptions.OverlappingBookingException;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.MeetingRoomBookingMapper;
import com.inventory.management.Model.MeetingRoomBooking;
import com.inventory.management.ReposiotryServices.MeetingBookingReposiotryService;
import com.inventory.management.service.MeetingRoomBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingRoomBookingServiceImpl implements MeetingRoomBookingService {
    private final MeetingBookingReposiotryService reposiotryService;
    private final MeetingRoomBookingMapper mapper;

    @Override
    public MeetingRoomBookingDTO createBooking(MeetingRoomBookingDTO request) {
        if (request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().isEqual(request.getEndTime())) {
            throw new OverlappingBookingException("Start time must be before end time");

        }
        reposiotryService.overlappingBooking(request);

        MeetingRoomBooking entity = mapper.toEntity(request);
        MeetingRoomBooking meetingRoomBooking = reposiotryService.saveMeetingBooking(entity);


        return mapper.toDTO(meetingRoomBooking);
    }

    @Override
    public MeetingRoomBookingDTO getBookingById(Long id) {
        return reposiotryService.retriveBookingByIdFromDb(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with given id Not found" + id));
    }

    @Override
    public Page<MeetingRoomBookingDTO> getAllBookings(Long roomId, Pageable pageable) {
        Specification<MeetingRoomBooking> spec = (root, query, criteriaBuilder) -> {
            if (roomId != null) {
                return criteriaBuilder.equal(root.get("roomId"), roomId);
            }
            return criteriaBuilder.conjunction();
        };
        return reposiotryService.retriveAllBookingsFromDb(spec, pageable).map(mapper::toDTO);
    }

    @Override
    public MeetingRoomBookingDTO updateBooking(Long id, MeetingRoomBookingDTO request) {
        return mapper.toDTO(reposiotryService.updateMeetingBooking(id, request));
    }

    @Override
    public void cancelBooking(Long id) {
        reposiotryService.deleteMeetingBooking(id);
    }
}
