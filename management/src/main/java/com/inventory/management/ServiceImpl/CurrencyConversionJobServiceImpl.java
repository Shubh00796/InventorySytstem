package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.CurrencyConversionJobDto;
import com.inventory.management.Dtos.CurrencyConversionJobRequestDto;
import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.ConversionType;
import com.inventory.management.FactoryInterfaces.CurrencyConversionStrategy;
import com.inventory.management.FactoryInterfaces.CurrencyConversionStrategyFactory;
import com.inventory.management.Mapper.CurrencyConversionJobMapper;
import com.inventory.management.Model.CurrencyConversionJob;
import com.inventory.management.Repo.CurrencyConversionJobRepository;
import com.inventory.management.service.CurrencyConversionJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CurrencyConversionJobServiceImpl implements CurrencyConversionJobService {
    private final CurrencyConversionJobRepository repository;
    private final CurrencyConversionJobMapper mapper;
    private final CurrencyConversionStrategyFactory strategyFactory;

    @Override
    public List<CurrencyConversionJobDto> getAllJobs() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyConversionJobDto getJobById(Long id) {
        CurrencyConversionJob job = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        return mapper.toDto(job);
    }

    @Override
    public CurrencyConversionJobDto createJob(CurrencyConversionJobRequestDto requestDto) {

        CurrencyConversionJob savedToEntity = mapper.toEntity(requestDto);
        savedToEntity.setConversionStatus(ConversionStatus.PENDING);
        CurrencyConversionJob savedToRepo = repository.save(savedToEntity);

        return mapper.toDto(savedToRepo);
    }

    @Override
    public CurrencyConversionJobDto updateJob(Long id, CurrencyConversionJobRequestDto requestDto) {
        CurrencyConversionJob existingJob = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        updateJobFields(existingJob, requestDto);
        existingJob = processConversion(existingJob);
        CurrencyConversionJob updatedJob = repository.save(existingJob);
        return mapper.toDto(updatedJob);
    }

    private CurrencyConversionJob processConversion(CurrencyConversionJob job) {
        try {
            ConversionType type = job.getConversionType();
            CurrencyConversionStrategy strategy = strategyFactory.getCurrencyConversionStrategy(type);
            job.setConversionStatus(ConversionStatus.IN_PROGRESS);
            BigDecimal convertedAmount = strategy.convert(job.getAmount(), job.getSourceCurrency(), job.getTargetCurrency());
            job.setConvertedAmount(convertedAmount);
            job.setConversionStatus(ConversionStatus.COMPLETED);
        } catch (Exception ex) {
            job.setConversionStatus(ConversionStatus.FAILED);
            log.error("Conversion failed for job {}: {}", job.getId(), ex.getMessage());
            throw new RuntimeException("Conversion failed: " + ex.getMessage());
        }
        return job;
    }

    @Override
    public void deleteJob(Long id) {
        CurrencyConversionJob existingJob = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        repository.delete(existingJob);
    }


    private void updateJobFields(CurrencyConversionJob job, CurrencyConversionJobRequestDto requestDto) {
        Optional.ofNullable(requestDto.getSourceCurrency()).ifPresent(job::setSourceCurrency);
        Optional.ofNullable(requestDto.getTargetCurrency()).ifPresent(job::setTargetCurrency);
        Optional.ofNullable(requestDto.getAmount()).ifPresent(job::setAmount);
        Optional.ofNullable(requestDto.getConversionType()).ifPresent(job::setConversionType);

        job.setUpdatedAt(LocalDateTime.now());
    }

}