package com.inventory.management.Repo;

import com.inventory.management.Enums.RentalStatus;
import com.inventory.management.Model.RentalBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalBookingRepository extends JpaRepository<RentalBooking, Long> {
    Page<RentalBooking> findByStatus(RentalStatus status, Pageable pageable);
    Page<RentalBooking> findByEquipmentId(Long equipmentId, Pageable pageable);
    Page<RentalBooking> findByRenterId(Long renterId, Pageable pageable);
    Page<RentalBooking> findByStatusAndEquipmentId(RentalStatus status, Long equipmentId, Pageable pageable);
    Page<RentalBooking> findByStatusAndRenterId(RentalStatus status, Long renterId, Pageable pageable);
}