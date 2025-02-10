package com.inventory.management.service;

import com.inventory.management.Dtos.DocumentConversionJobDto;
import com.inventory.management.Dtos.DocumentConversionJobRequestDto;

import java.util.List;

public interface DocumentConversionJobService {
    List<DocumentConversionJobDto> getAllJobs();
    DocumentConversionJobDto getJobById(Long id);
    DocumentConversionJobDto createJob(DocumentConversionJobRequestDto jobRequestDto);
    DocumentConversionJobDto updateJob(Long id, DocumentConversionJobRequestDto jobRequestDto);
    void deleteJob(Long id);
}