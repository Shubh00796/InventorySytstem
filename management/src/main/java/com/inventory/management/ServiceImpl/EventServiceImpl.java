package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.EventDTO;
import com.inventory.management.Mapper.EventMapper;
import com.inventory.management.ReposiotryServices.EventReposiotryService;
import com.inventory.management.service.EventService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventReposiotryService eventReposiotryService;

    private static final String EVENT_SERVICE = "eventService";

    @Override
    @CircuitBreaker(name = EVENT_SERVICE, fallbackMethod = "com.inventory.management.fallbacks.EventFallbackService.fallbackGetAllEvents")
    public List<EventDTO> getAllEvents() {
        return eventReposiotryService.getAllEvents()
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = EVENT_SERVICE, fallbackMethod = "com.inventory.management.fallbacks.EventFallbackService.fallbackGetEventById")
    public Optional<EventDTO> getEventById(Long id) {
        return eventReposiotryService.findById(id)
                .map(eventMapper::toDTO);
    }

    @Override
    @CircuitBreaker(name = EVENT_SERVICE, fallbackMethod = "com.inventory.management.fallbacks.EventFallbackService.fallbackCreateEvent")
    public EventDTO createEvent(EventDTO eventDTO) {
        return eventMapper.toDTO(eventReposiotryService.save(eventMapper.toEntity(eventDTO)));
    }

    @Override
    @CircuitBreaker(name = EVENT_SERVICE, fallbackMethod = "com.inventory.management.fallbacks.EventFallbackService.fallbackUpdateEvent")
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventMapper.toDTO(eventReposiotryService.update(id, eventDTO));
    }

    @Override
    @CircuitBreaker(name = EVENT_SERVICE, fallbackMethod = "com.inventory.management.fallbacks.EventFallbackService.fallbackDeleteEvent")
    public void deleteEvent(Long id) {
        eventReposiotryService.delete(id);
    }
}
