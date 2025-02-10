package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.ImageTransformationJobDto;
import com.inventory.management.Dtos.ImageTransformationJobRequestDto;
import com.inventory.management.Enums.TransformationStatus;
import com.inventory.management.Enums.TransformationType;
import com.inventory.management.FactoryInterfaces.ImageTransformationStrategy;
import com.inventory.management.FactoryInterfaces.ImageTransformationStrategyFactory;
import com.inventory.management.Mapper.ImageTransformationJobMapper;
import com.inventory.management.Model.ImageTransformationJob;
import com.inventory.management.Repo.ImageTransformationJobRepository;
import com.inventory.management.service.ImageTransformationJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageTransformationJobServiceImpl implements ImageTransformationJobService {

    private final ImageTransformationJobRepository repository;
    private final ImageTransformationJobMapper mapper;
    private final ImageTransformationStrategyFactory strategyFactory;

    @Override
    public List<ImageTransformationJobDto> getAllJobs() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ImageTransformationJobDto getJobById(Long id) {
        ImageTransformationJob job = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        return mapper.toDTO(job);
    }


    @Override
    public ImageTransformationJobDto createJob(ImageTransformationJobRequestDto requestDto) {
        ImageTransformationJob job = buildJob(requestDto);
        ImageTransformationJob imageTransformationJob = processTransformation(job);
        ImageTransformationJob savedToRepo = repository.save(imageTransformationJob);
        return mapper.toDTO(savedToRepo);

    }

    @Override
    public ImageTransformationJobDto updateJob(Long id, ImageTransformationJobRequestDto requestDto) {
        return getImageTransformationJobDto(id, requestDto);
    }

    private ImageTransformationJobDto getImageTransformationJobDto(Long id, ImageTransformationJobRequestDto requestDto) {
        ImageTransformationJob existingJob = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        updateJobFields(existingJob, requestDto);
        existingJob = processTransformation(existingJob);
        ImageTransformationJob updatedJob = repository.save(existingJob);
        return mapper.toDTO(updatedJob);
    }

    private ImageTransformationJob buildJob(ImageTransformationJobRequestDto requestDto) {
        return ImageTransformationJob.builder()
                .imageName(requestDto.getImageName())
                .transformationType(requestDto.getTransformationType())
                .originalImage(requestDto.getOriginalImage())
                .transformationStatus(TransformationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private ImageTransformationJob processTransformation(ImageTransformationJob job) {
        try {
            TransformationType type = job.getTransformationType();
            ImageTransformationStrategy strategy = strategyFactory.getTransformationStrategy(type);
            job.setTransformationStatus(TransformationStatus.IN_PROGRESS);
            String transformedImage = strategy.transform(job.getOriginalImage());
            job.setTransformedImage(transformedImage);
            job.setTransformationStatus(TransformationStatus.COMPLETED);
        } catch (Exception ex) {
            job.setTransformationStatus(TransformationStatus.FAILED);
            log.error("Transformation failed for job {}: {}", job.getId(), ex.getMessage());
            throw new RuntimeException("Transformation failed: " + ex.getMessage());
        }
        return job;
    }

    private void updateJobFields(ImageTransformationJob job, ImageTransformationJobRequestDto requestDto) {
        Optional.ofNullable(requestDto.getImageName()).ifPresent(job::setImageName);
        Optional.ofNullable(requestDto.getTransformationType()).ifPresent(job::setTransformationType);
        Optional.ofNullable(requestDto.getOriginalImage()).ifPresent(job::setOriginalImage);

        job.setUpdatedAt(LocalDateTime.now());
    }


    @Override
    public void deleteJob(Long id) {
        ImageTransformationJob existingJob = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        repository.delete(existingJob);
    }
}
