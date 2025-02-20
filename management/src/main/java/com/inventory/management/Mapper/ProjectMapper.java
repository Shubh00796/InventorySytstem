package com.inventory.management.Mapper;

import com.inventory.management.Dtos.EVReservationDTO;
import com.inventory.management.Dtos.RentalBookingDTO;
import com.inventory.management.Enums.EVReservationStatus;
import com.inventory.management.Enums.RentalStatus;
import com.inventory.management.Model.EVReservation;
import com.inventory.management.Model.RentalBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {EVReservationStatus.class})
public interface EVReservationMapper {

    @Mapping(target = "status", expression = "java(EVReservationStatus.valueOf(evReservationDTO.getStatus().toUpperCase()))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    EVReservation toEntity(EVReservationDTO evReservationDTO);

        EVReservationDTO toDTO(EVReservation evReservation);
}
