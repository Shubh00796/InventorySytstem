package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.QueueTicketDTO;
import com.inventory.management.Enums.TicketStatus;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.TicketMapper;
import com.inventory.management.Model.QueueTicket;
import com.inventory.management.ReposiotryServices.QueueTicketReposiotryService;
import com.inventory.management.events.TicketCreatedEvent;
import com.inventory.management.service.QueueTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueTicketServiceImpl implements QueueTicketService {
    private final ApplicationEventPublisher eventPublisher;
    private final QueueTicketReposiotryService reposiotryService;
    private final TicketMapper mapper;
    private final ConcurrentHashMap<Long, ReentrantLock> queueLocks = new ConcurrentHashMap<>();


    @Override
    public QueueTicketDTO createTicket(QueueTicketDTO queueTicketDTO) {
        QueueTicket entity = mapper.toEntity(queueTicketDTO);
        entity.setCreatedAt(LocalDateTime.now());
        int currentQueueSize = reposiotryService.retriveAllQueueTickets().size();
        entity.setEstimatedWaitTime((currentQueueSize + 1) * 5);
        entity.setStatus(TicketStatus.WAITING);

        QueueTicket savedTicket = reposiotryService.saveQueueTicket(entity);
        log.info("Created ticket with ID: {}", savedTicket.getId());
        eventPublisher.publishEvent(new TicketCreatedEvent(this, savedTicket));


        return mapper.toDto(savedTicket);
    }

    @Override
    public QueueTicketDTO getTicketById(Long id) {
        QueueTicket queueTicket = reposiotryService.retriveQueueTicketById(id);
        return mapper.toDto(queueTicket);
    }

    @Override
    public List<QueueTicketDTO> getAllTickets() {
        return reposiotryService.retriveAllQueueTickets()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public QueueTicketDTO updateTicketStatus(Long queueTicketId, QueueTicketDTO queueTicketDTO) {
        validateTicketId(queueTicketId, queueTicketDTO);
        ReentrantLock reentrantLock = queueLocks.computeIfAbsent(queueTicketId, id -> new ReentrantLock());
        reentrantLock.lock();
        try {
            QueueTicket queueTicket = updateQueueTicketsIfNotNull(queueTicketId, queueTicketDTO);
            return mapper.toDto(reposiotryService.saveQueueTicket(queueTicket
            ));
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        } finally {
            reentrantLock.lock();
        }
    }

    private QueueTicket updateQueueTicketsIfNotNull(Long queueTicketId, QueueTicketDTO queueTicketDTO) {
        QueueTicket queueTicket = reposiotryService.retriveQueueTicketById(queueTicketId);
        mapper.updateEntityFromDto(queueTicketDTO, queueTicket);
        return queueTicket;
    }

    private static void validateTicketId(Long queueTicketId, QueueTicketDTO queueTicketDTO) {
        if (queueTicketId == null) {
            throw new IllegalArgumentException("queueTicketId can not be null" + queueTicketDTO);


        }
    }
}
