package com.inventory.management.Mapper;

import com.inventory.management.Dtos.MeetingRoomBookingDTO;
import com.inventory.management.Model.MeetingRoomBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface MeetingRoomBookingMapper {

    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "localDateTimeToString")
    MeetingRoomBookingDTO toDTO(MeetingRoomBooking meetingRoomBooking);

    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "stringToLocalDateTime")
    MeetingRoomBooking toEntity(MeetingRoomBookingDTO meetingRoomBookingDTO);

    @Named("localDateTimeToString")
    static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }

    @Named("stringToLocalDateTime")
    static LocalDateTime stringToLocalDateTime(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }
}
