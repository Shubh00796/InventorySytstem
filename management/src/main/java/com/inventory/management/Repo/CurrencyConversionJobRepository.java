package com.inventory.management.Repo;

import com.inventory.management.Model.CurrencyConversionJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionJobRepository extends JpaRepository<CurrencyConversionJob, Long> {


}