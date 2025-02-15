package com.inventory.management.ServiceImpl;


import com.inventory.management.Dtos.EVReservationDTO;
import com.inventory.management.Mapper.EVReservationMapper;
import com.inventory.management.Model.EVReservation;
import com.inventory.management.ReposiotryServices.EvReservationRepositoryService;
import com.inventory.management.service.EVReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EVReservationServiceImpl implements EVReservationService {

    private final EvReservationRepositoryService repositoryService;
    private final EVReservationMapper mapper;


    @Override
    public List<EVReservationDTO> getReservations() {
        return repositoryService.getAllReservations()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }



    @Override
    public EVReservationDTO createReservation(EVReservationDTO request) {
        log.info("Creating reservation for Station ID: {}, User ID: {}", request.getStationId(), request.getUserId());

        EVReservation reservation = mapper.toEntity(request);
        EVReservation savedReservation = repositoryService.createReservation(reservation);

        return mapper.toDTO(savedReservation);
    }

    @Override
    public EVReservationDTO updateReservation(Long id, EVReservationDTO request) {
        log.info("Updating reservation with ID: {}", id);

        EVReservation updatedReservation = repositoryService.updateReservation(id, request);

        return mapper.toDTO(updatedReservation);
    }

    @Override
    public EVReservationDTO confirmReservation(Long id) {
        log.info("Confirming reservation with ID: {}", id);

        EVReservation confirmedReservation = repositoryService.confirmReservation(id);

        return mapper.toDTO(confirmedReservation);
    }

    @Override
    public EVReservationDTO cancelReservation(Long id) {
        log.info("Cancelling reservation with ID: {}", id);

        EVReservation cancelledReservation = repositoryService.cancelReservation(id);

        return mapper.toDTO(cancelledReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        log.info("Deleting reservation with ID: {}", id);
        repositoryService.deleteReservation(id);
    }
}
