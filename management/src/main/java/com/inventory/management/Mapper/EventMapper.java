package com.inventory.management.Mapper;


import com.inventory.management.Dtos.EventDTO;
import com.inventory.management.Model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO toDTO(Event event);
    Event toEntity(EventDTO eventDTO);
}