package com.inventory.management.Repo;

import com.inventory.management.Model.EnergyMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyMeterRepository extends JpaRepository<EnergyMeter, Long> {
    EnergyMeter findByMeterId(String meterId);
}