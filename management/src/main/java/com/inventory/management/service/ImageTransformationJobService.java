package com.inventory.management.service;

import com.inventory.management.Dtos.ImageTransformationJobDto;
import com.inventory.management.Dtos.ImageTransformationJobRequestDto;

import java.util.List;

public interface ImageTransformationJobService {
    List<ImageTransformationJobDto> getAllJobs();
    ImageTransformationJobDto getJobById(Long id);
    ImageTransformationJobDto createJob(ImageTransformationJobRequestDto requestDto);
    ImageTransformationJobDto updateJob(Long id, ImageTransformationJobRequestDto requestDto);
    void deleteJob(Long id);
}