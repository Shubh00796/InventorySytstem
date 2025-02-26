package com.inventory.management.events;

import com.inventory.management.Model.QueueTicket;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TicketCreatedEvent extends ApplicationEvent {
    private final QueueTicket queueTicket;

    public TicketCreatedEvent(Object source, QueueTicket queueTicket) {
        super(source);
        this.queueTicket = queueTicket;
    }
}
