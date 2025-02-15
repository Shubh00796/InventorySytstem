package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.RentalBookingDTO;
import com.inventory.management.Enums.RentalStatus;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.RentalBooking;
import com.inventory.management.Repo.RentalBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RentalReposiotrService {
    private final RentalBookingRepository repository;

    public Optional<RentalBooking> getById(Long id) {
        return repository.findById(id);
    }

    public Page<RentalBooking> getRentalBookingsByIds(String status, Long equipmentId, Long renterId, Pageable pageable) {
        return repository.findByStatusAndEquipmentId(RentalStatus.valueOf(status), equipmentId, pageable);
    }


    public RentalBooking saveRentalBooking(RentalBooking rentalBooking) {
        return repository.save(rentalBooking);
    }

    public RentalBooking updateBooking(Long id, RentalBookingDTO request) {
        RentalBooking rentalBooking = updateRentalBookingIfNotNull(id, request);
        return repository.save(rentalBooking);
    }


    public void deleteRentalBooking(Long id) {
        RentalBooking rentalBooking = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found for given id" + id));
        if (rentalBooking.getStatus() == RentalStatus.COMPLETED) {
            throw new IllegalStateException("Cannot delete a completed booking");
        }
        repository.delete(rentalBooking);


    }

    private RentalBooking updateRentalBookingIfNotNull(Long id, RentalBookingDTO request) {
        RentalBooking rentalBooking = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found for given id" + id));

        checkStatus(request, rentalBooking);

        Optional.ofNullable(request.getEquipmentId()).ifPresent(rentalBooking::setEquipmentId);
        Optional.ofNullable(request.getStartDate()).ifPresent(rentalBooking::setStartDate);
        Optional.ofNullable(request.getEndDate()).ifPresent(rentalBooking::setEndDate);
        Optional.ofNullable(request.getRenterId()).ifPresent(rentalBooking::setRenterId);
        rentalBooking.setStatus(RentalStatus.REQUESTED);
        rentalBooking.setUpdatedAt(LocalDateTime.now());
        return rentalBooking;
    }

    private static void checkStatus(RentalBookingDTO request, RentalBooking rentalBooking) {
        if (rentalBooking.getStatus() == RentalStatus.COMPLETED) {
            throw new IllegalStateException("Cannot modify a completed booking");
        }
        if (rentalBooking.getStatus() == RentalStatus.CANCELLED && request.getStatus().equals("CONFIRMED")) {
            throw new IllegalStateException("Cannot confirm a cancelled booking");
        }
    }

}
