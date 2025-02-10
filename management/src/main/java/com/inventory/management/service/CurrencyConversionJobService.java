package com.inventory.management.service;

import com.inventory.management.Dtos.CurrencyConversionJobDto;
import com.inventory.management.Dtos.CurrencyConversionJobRequestDto;

import java.util.List;

public interface CurrencyConversionJobService {
    List<CurrencyConversionJobDto> getAllJobs();
    CurrencyConversionJobDto getJobById(Long id);
    CurrencyConversionJobDto createJob(CurrencyConversionJobRequestDto requestDto);
    CurrencyConversionJobDto updateJob(Long id, CurrencyConversionJobRequestDto requestDto);
    void deleteJob(Long id);
}