package com.inventory.management.service;



import com.inventory.management.Dtos.MeterReadingDTO;
import com.inventory.management.Model.MeterReading;

import java.util.List;
import java.util.Optional;

public interface MeterReadingService {
    List<MeterReadingDTO> getAll();
    Optional<MeterReadingDTO> getById(Long id);
    MeterReadingDTO create(String vendor, String payload);
    MeterReadingDTO update(Long id, MeterReadingDTO meterReadingDTO);
    void delete(Long id);
}
