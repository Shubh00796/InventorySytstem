package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.DocumentConversionJobDto;
import com.inventory.management.Dtos.DocumentConversionJobRequestDto;
import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.DocumentType;
import com.inventory.management.Exceptions.DocumentConversionException;
import com.inventory.management.FactoryInterfaces.DocumentConverter;
import com.inventory.management.FactoryInterfaces.DocumentConverterFactory;
import com.inventory.management.Mapper.DocumentConversionJobMapper;
import com.inventory.management.Model.DocumentConversionJob;
import com.inventory.management.Repo.DocumentConversionJobRepository;
import com.inventory.management.service.DocumentConversionJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DocumentConversionJobServiceImpl implements DocumentConversionJobService {

    private final DocumentConversionJobRepository repository;
    private final DocumentConversionJobMapper mapper;
    private final DocumentConverterFactory converterFactory;


    @Override
    public List<DocumentConversionJobDto> getAllJobs() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentConversionJobDto getJobById(Long id) {
        DocumentConversionJob job = repository.findById(id)
                .orElseThrow(() -> new DocumentConversionException("Job not found with id: " + id));
        return mapper.toDTO(job);
    }

    @Override
    public DocumentConversionJobDto createJob(DocumentConversionJobRequestDto jobRequestDto) {
        DocumentConversionJob job = getDocumentConversionJob(jobRequestDto);

        job = processConversion(job);
        DocumentConversionJob savedJob = repository.save(job);
        return mapper.toDTO(savedJob);
    }

    @Override
    public DocumentConversionJobDto updateJob(Long id, DocumentConversionJobRequestDto jobRequestDto) {

        DocumentConversionJob existingJob = repository.findById(id)
                .orElseThrow(() -> new DocumentConversionException("Job not found with id: " + id));

        updateFieldsIfNotNull(jobRequestDto, existingJob);

        existingJob = processConversion(existingJob);
        DocumentConversionJob updatedJob = repository.save(existingJob);
        return mapper.toDTO(updatedJob);
    }

    private static void updateFieldsIfNotNull(DocumentConversionJobRequestDto jobRequestDto, DocumentConversionJob existingJob) {
        if (jobRequestDto.getDocumentName() != null) {
            existingJob.setDocumentName(jobRequestDto.getDocumentName());
        }
        if (jobRequestDto.getDocumentType() != null) {
            existingJob.setDocumentType(jobRequestDto.getDocumentType());
        }
        if (jobRequestDto.getSourceContent() != null) {
            existingJob.setSourceContent(jobRequestDto.getSourceContent());
        }
        existingJob.setUpdatedAt(LocalDateTime.now());
    }


    @Override
    public void deleteJob(Long id) {

    }


    private DocumentConversionJob processConversion(DocumentConversionJob job) {
        try {
            DocumentType type = job.getDocumentType();
            DocumentConverter converter = converterFactory.getDocumentConverter(type);
            job.setConversionStatus(ConversionStatus.IN_PROGRESS);
            String convertedContent = converter.convert(job.getSourceContent());
            job.setConvertedContent(convertedContent);
            job.setConversionStatus(ConversionStatus.COMPLETED);
        } catch (Exception ex) {
            job.setConversionStatus(ConversionStatus.FAILED);
            throw new DocumentConversionException("Conversion failed: " + ex.getMessage());
        }
        return job;
    }

    private static DocumentConversionJob getDocumentConversionJob(DocumentConversionJobRequestDto jobRequestDto) {
        DocumentConversionJob job = DocumentConversionJob.builder()
                .documentName(jobRequestDto.getDocumentName())
                .documentType(jobRequestDto.getDocumentType())
                .sourceContent(jobRequestDto.getSourceContent())
                .conversionStatus(ConversionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return job;
    }
}
