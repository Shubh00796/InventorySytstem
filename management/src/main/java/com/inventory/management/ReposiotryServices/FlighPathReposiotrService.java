package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.FlightPathNotFoundException;
import com.inventory.management.Model.DroneFlightPath;
import com.inventory.management.Repo.DroneFlightPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlighPathReposiotrService {
    private final DroneFlightPathRepository repository;

    public List<DroneFlightPath> retriveAllFlightPaths() {
        return repository.findAll();
    }

    public DroneFlightPath retriveFlighById(Long flightId) {
        return getDroneFlightPath(flightId);
    }

    private DroneFlightPath getDroneFlightPath(Long flightId) {
        return repository.findById(flightId)
                .orElseThrow(() -> new FlightPathNotFoundException("Flight with given id not found" + flightId));
    }

    public DroneFlightPath saveDroneFlightPath(DroneFlightPath droneFlightPath) {
        return repository.save(droneFlightPath);
    }

    public void deletedroneFlightPath(Long flightId) {
        repository.delete(getDroneFlightPath(flightId));

    }
}
