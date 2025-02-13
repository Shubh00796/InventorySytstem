package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.MeterReadingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.MeterReading;
import com.inventory.management.Repo.MeterReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeterReadingReposiotryService {
    private final MeterReadingRepository meterReadingRepository;

    public List<MeterReading> getAllReadings() {
        return meterReadingRepository.findAll();
    }

    public Optional<MeterReading> getReadingById(Long id) {
        return meterReadingRepository.findById(id);
    }

    public MeterReading saveRaeding(MeterReading meterReading) {
        return meterReadingRepository.save(meterReading);
    }

    public MeterReading updateReading(Long id, MeterReadingDTO meterReadingDTO) {
        MeterReading meterReading = meterReadingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("redaing with given id not found" + id));

        Optional.ofNullable(meterReadingDTO.getVendor()).ifPresent(meterReading::setVendor);
        Optional.ofNullable(meterReadingDTO.getConsumption()).ifPresent(meterReading::setConsumption);

        return meterReadingRepository.save(meterReading);
    }

    public void deleteReading(Long id) {
        MeterReading meterReading = meterReadingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("redaing with given id not found" + id));
        meterReadingRepository.delete(meterReading);
    }
}
