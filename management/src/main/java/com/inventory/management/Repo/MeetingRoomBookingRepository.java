package com.inventory.management.Repo;

import com.inventory.management.Model.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomBookingRepository extends JpaRepository<MeetingRoomBooking, Long>, JpaSpecificationExecutor<MeetingRoomBooking> {
    List<MeetingRoomBooking> findByRoomIdAndStartTimeLessThanAndEndTimeGreaterThan(Long roomId, LocalDateTime endTime, LocalDateTime startTime);
}