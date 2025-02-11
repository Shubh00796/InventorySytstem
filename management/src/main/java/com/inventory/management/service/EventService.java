package com.inventory.management.service;

import com.inventory.management.Dtos.EventDTO;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<EventDTO> getAllEvents();
    Optional<EventDTO> getEventById(Long id);
    EventDTO createEvent(EventDTO eventDTO);
    EventDTO updateEvent(Long id, EventDTO eventDTO);
    void deleteEvent(Long id);
}