package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.EnergyMeter;
import com.inventory.management.Repo.EnergyMeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnergyMeterRepositoryService {
    private final EnergyMeterRepository repository;

    public List<EnergyMeter> retrieveAllEnergies(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public Optional<EnergyMeter> retrieveEnergyMeterById(Long id) {
        return repository.findById(id);
    }

    public EnergyMeter updateConsumption(EnergyMeter meter, double newReading) {
        meter.setCurrentConsumption(newReading);
        return repository.save(meter);
    }

    public boolean isThresholdExceeded(Long meterId) {
        return repository.findById(meterId)
                .map(meter -> meter.getCurrentConsumption() > meter.getThreshold())
                .orElse(false);
    }

    public EnergyMeter saveEnergyMeter(EnergyMeter energyMeter) {
        return repository.save(energyMeter);
    }

    public void deleteEnergyMeter(Long id) {
        EnergyMeter energyMeter = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Energy Meter not found with ID: " + id));
        repository.delete(energyMeter);
    }
}
