package com.inventory.management.Repo;

import com.inventory.management.Model.QueueTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueTicketRepository extends JpaRepository<QueueTicket, Long> {
}