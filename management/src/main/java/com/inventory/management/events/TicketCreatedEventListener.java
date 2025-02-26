package com.inventory.management.events;

import com.inventory.management.Model.QueueTicket;
import com.inventory.management.ReposiotryServices.QueueTicketReposiotryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketCreatedEventListener {
    private final QueueTicketReposiotryService reposiotryService;


    @Async
    @EventListener
    @Transactional
    public void handleTicketCreatedEvent(TicketCreatedEvent event) {
        QueueTicket ticket = event.getQueueTicket();
        if (ticket.getEstimatedWaitTime() > 30) {
            log.warn("Ticket ID {} has a long wait time of {} minutes", ticket.getId(), ticket.getEstimatedWaitTime());
            // Additional actions (e.g., send notification) can be implemented here.
        }

    }
}
