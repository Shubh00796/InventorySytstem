package com.inventory.management.service;

import com.inventory.management.Dtos.EnergyMeterDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface EnergyMeterService {

    EnergyMeterDTO createEnergyMeter(EnergyMeterDTO energyMeterDTO);

    EnergyMeterDTO updateEnergyMeter(Long id, EnergyMeterDTO energyMeterDTO);

    EnergyMeterDTO recordEnergyConsumption(Long id, double newConsumption);

    EnergyMeterDTO getEnergyMeterById(String meterId);

    @Cacheable(value = "meter", key = "#id")
    EnergyMeterDTO getEnergyMeterById(Long id);

    List<EnergyMeterDTO> getAllEnergyMeters(int page, int size);

    void deleteEnergyMeter(Long id);

}
