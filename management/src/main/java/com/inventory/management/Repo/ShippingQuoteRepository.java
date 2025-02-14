package com.inventory.management.Repo;

import com.inventory.management.Model.ShippingQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingQuoteRepository extends JpaRepository<ShippingQuote, Long> {
}