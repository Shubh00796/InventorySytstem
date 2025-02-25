package com.inventory.management.service;

import com.inventory.management.Dtos.DroneFlightPathDTO;

import java.util.List;

public interface DroneFlightPathService {
    DroneFlightPathDTO createFlightPath(DroneFlightPathDTO flightPathDTO);

    DroneFlightPathDTO getFlightPathById(Long id);

    List<DroneFlightPathDTO> getAllFlightPaths();

    DroneFlightPathDTO updateFlightPath(Long id, DroneFlightPathDTO flightPathDTO);

    void deleteFlightPath(Long id);
}