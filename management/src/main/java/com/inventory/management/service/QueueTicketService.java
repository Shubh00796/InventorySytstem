package com.inventory.management.service;

import com.inventory.management.Dtos.QueueTicketDTO;

import java.util.List;

public interface QueueTicketService {
    QueueTicketDTO createTicket(QueueTicketDTO queueTicketDTO);

    QueueTicketDTO getTicketById(Long id);

    List<QueueTicketDTO> getAllTickets();

    QueueTicketDTO updateTicketStatus(Long id, QueueTicketDTO queueTicketDTO);
}