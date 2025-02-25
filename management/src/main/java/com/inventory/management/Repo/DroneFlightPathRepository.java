package com.inventory.management.Repo;

import com.inventory.management.Model.DroneFlightPath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneFlightPathRepository extends JpaRepository<DroneFlightPath, Long> {
}