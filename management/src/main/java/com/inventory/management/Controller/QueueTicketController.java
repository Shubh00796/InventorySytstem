package com.inventory.management.Controller;

import com.inventory.management.Dtos.QueueTicketDTO;
import com.inventory.management.service.QueueTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue-tickets")
@RequiredArgsConstructor
@Slf4j
public class QueueTicketController {

    private final QueueTicketService queueTicketService;

    @PostMapping
    public ResponseEntity<QueueTicketDTO> createTicket(@RequestBody QueueTicketDTO queueTicketDTO) {
        log.info("Received request to create a new ticket: {}", queueTicketDTO);
        QueueTicketDTO createdTicket = queueTicketService.createTicket(queueTicketDTO);
        return ResponseEntity.ok(createdTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueueTicketDTO> getTicketById(@PathVariable Long id) {
        log.info("Fetching ticket with ID: {}", id);
        return ResponseEntity.ok(queueTicketService.getTicketById(id));
    }

    @GetMapping
    public ResponseEntity<List<QueueTicketDTO>> getAllTickets() {
        log.info("Fetching all queue tickets");
        return ResponseEntity.ok(queueTicketService.getAllTickets());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<QueueTicketDTO> updateTicketStatus(
            @PathVariable Long id,
            @RequestBody QueueTicketDTO queueTicketDTO) {
        log.info("Updating status of ticket with ID: {}", id);
        return ResponseEntity.ok(queueTicketService.updateTicketStatus(id, queueTicketDTO));
    }
}
