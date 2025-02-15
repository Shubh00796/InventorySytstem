package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.RentalBookingDTO;
import com.inventory.management.Enums.RentalStatus;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.RentalBookingMapper;
import com.inventory.management.Model.RentalBooking;
import com.inventory.management.ReposiotryServices.RentalReposiotrService;
import com.inventory.management.service.RentalBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.inventory.management.Enums.RentalStatus.REQUESTED;

@Service
@RequiredArgsConstructor
public class RentalBookingServiceImpl implements RentalBookingService {
    private final RentalBookingMapper mapper;
    private final RentalReposiotrService rentalReposiotrService;

    @Value("${feature.rental.enabled:true}")
    private boolean rentalFeatureEnabled;

    private void checkFeatureEnabled() {
        if (!rentalFeatureEnabled) {
            throw new RuntimeException("Rental feature is disabled");
        }
    }


    @Override
    public Optional<RentalBookingDTO> getRentalBooking(Long id) {
        checkFeatureEnabled();

        return Optional.ofNullable(rentalReposiotrService.getById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Id with given booking not found" + id)));


    }


    @Override
    public Page<RentalBookingDTO> getRentalBookings(String status, Long equipmentId, Long renterId, Pageable pageable) {
        checkFeatureEnabled();

        return rentalReposiotrService.getRentalBookingsByIds(status, equipmentId, renterId, pageable)
                .map(mapper::toDTO);
    }

    @Override
    public RentalBookingDTO createRentalBooking(RentalBookingDTO request) {
        checkFeatureEnabled();

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new ResourceNotFoundException("End date must be after start date");
        }
        RentalBooking entity = mapper.toEntity(request);
        entity.setStatus(REQUESTED);
        entity.setCreatedAt(LocalDateTime.now());
        return mapper.toDTO(rentalReposiotrService.saveRentalBooking(entity));
    }

    @Override
    public RentalBookingDTO updateRentalBooking(Long id, RentalBookingDTO request) {
        checkFeatureEnabled();

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new ResourceNotFoundException("End date must be after start date");
        }

        return mapper.toDTO(rentalReposiotrService.updateBooking(id, request));
    }

    @Override
    public void deleteRentalBooking(Long id) {
        checkFeatureEnabled();

        rentalReposiotrService.deleteRentalBooking(id);

    }
}
