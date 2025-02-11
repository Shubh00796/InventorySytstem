package com.inventory.management.fallbacks;

import com.inventory.management.Dtos.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class EventFallbackService {

    public static List<EventDTO> fallbackGetAllEvents(Throwable t) {
        log.error("Fallback for getAllEvents: {}", t.getMessage());
        return List.of();
    }

    public static Optional<EventDTO> fallbackGetEventById(Long id, Throwable t) {
        log.error("Fallback for getEventById: {}", t.getMessage());
        return Optional.empty();
    }

    public static EventDTO fallbackCreateEvent(EventDTO eventDTO, Throwable t) {
        log.error("Fallback for createEvent: {}", t.getMessage());
        throw new RuntimeException("Service unavailable, please try again later");
    }

    public static EventDTO fallbackUpdateEvent(Long id, EventDTO eventDTO, Throwable t) {
        log.error("Fallback for updateEvent: {}", t.getMessage());
        throw new RuntimeException("Service unavailable, please try again later");
    }

    public static void fallbackDeleteEvent(Long id, Throwable t) {
        log.error("Fallback for deleteEvent: {}", t.getMessage());
        throw new RuntimeException("Service unavailable, please try again later");
    }
}
