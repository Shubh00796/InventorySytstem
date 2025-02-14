package com.inventory.management.Domain.Repo;

import com.inventory.management.Domain.Entity.Ddelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DdeliveryRepository extends JpaRepository<Ddelivery, UUID> {
}