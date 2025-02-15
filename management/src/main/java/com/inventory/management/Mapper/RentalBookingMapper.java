package com.inventory.management.Mapper;

import com.inventory.management.Dtos.RentalBookingDTO;
import com.inventory.management.Enums.RentalStatus;
import com.inventory.management.Model.RentalBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {RentalStatus.class})
public interface RentalBookingMapper {

    @Mapping(target = "status", expression = "java(RentalStatus.valueOf(rentalBookingDTO.getStatus().toUpperCase()))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    RentalBooking toEntity(RentalBookingDTO rentalBookingDTO);

    @Mapping(target = "status", expression = "java(rentalBooking.getStatus().name())")
    RentalBookingDTO toDTO(RentalBooking rentalBooking);
}
