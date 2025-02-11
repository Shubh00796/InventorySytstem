package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.EventDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Event;
import com.inventory.management.Repo.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventReposiotryService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Event save(Event event) {
        event.setEventDate(LocalDateTime.now());
        return eventRepository.save(event);
    }

    public Event update(Long id, EventDTO eventDTO) {
        Event event = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event  with ID " + id + " not found"));

        Optional.ofNullable(eventDTO.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(eventDTO.getName()).ifPresent(event::setName);
        Optional.ofNullable(eventDTO.getDescription()).ifPresent(event::setDescription);

        return eventRepository.save(event);


    }

    public void delete(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor reading not found with id: " + id));
        eventRepository.delete(event);
    }
}
