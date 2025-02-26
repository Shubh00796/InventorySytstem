package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.QueueTicket;
import com.inventory.management.Repo.QueueTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueTicketReposiotryService {
    private final QueueTicketRepository repository;

    public List<QueueTicket> retriveAllQueueTickets() {
        return repository.findAll();
    }

    public QueueTicket retriveQueueTicketById(Long ticketId) {
        return repository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Id with given ticket Not found" + ticketId));
    }

    public QueueTicket saveQueueTicket(QueueTicket queueTicket) {
        return repository.save(queueTicket);
    }
}
