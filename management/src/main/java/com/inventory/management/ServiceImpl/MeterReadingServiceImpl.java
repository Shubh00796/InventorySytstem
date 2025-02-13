package com.inventory.management.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.management.AdpatorFactory.MeterReadingAdapterFactory;
import com.inventory.management.Dtos.MeterReadingDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.MeterReadingMapper;
import com.inventory.management.Model.MeterReading;
import com.inventory.management.ReposiotryServices.MeterReadingReposiotryService;
import com.inventory.management.ValidatorLogics.MeterReadingPayloadProcessor;
import com.inventory.management.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeterReadingServiceImpl implements MeterReadingService {

    private final MeterReadingMapper mapper;

    private final MeterReadingReposiotryService reposiotryService;
    private final MeterReadingPayloadProcessor payloadProcessor; // Injecting the processor

    @Override
    public List<MeterReadingDTO> getAll() {
        return reposiotryService.getAllReadings()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MeterReadingDTO> getById(Long id) {
        return Optional.ofNullable(
                reposiotryService.getReadingById(id)
                        .map(mapper::toDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("Reading not found for given id"))
        );
    }

    @Override
    public MeterReadingDTO create(String vendor, String payload) {
        MeterReading meterReading = payloadProcessor.process(vendor, payload);

        MeterReading savedReading = reposiotryService.saveRaeding(meterReading);

        return mapper.toDTO(savedReading);
    }

    @Override
    public MeterReadingDTO update(Long id, MeterReadingDTO meterReadingDTO) {
        MeterReading updatedMeterReading = reposiotryService.updateReading(id, meterReadingDTO);
        return mapper.toDTO(updatedMeterReading);
    }

    @Override
    public void delete(Long id) {
        reposiotryService.deleteReading(id);
    }
}

