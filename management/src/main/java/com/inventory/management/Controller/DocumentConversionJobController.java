package com.inventory.management.Controller;

import com.inventory.management.Dtos.DocumentConversionJobDto;
import com.inventory.management.Dtos.DocumentConversionJobRequestDto;
import com.inventory.management.Utility.ConversionFallbackUtil;
import com.inventory.management.service.DocumentConversionJobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class DocumentConversionJobController {

    private final DocumentConversionJobService jobService;

    @GetMapping
    public ResponseEntity<List<DocumentConversionJobDto>> getAllJobs() {
        List<DocumentConversionJobDto> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "documentService", fallbackMethod = "fallbackGetJobById")
    public ResponseEntity<DocumentConversionJobDto> getJobById(@PathVariable Long id) {
        DocumentConversionJobDto jobDto = jobService.getJobById(id);
        return ResponseEntity.ok(jobDto);
    }

    @PostMapping
    @RateLimiter(name = "documentService")
    public ResponseEntity<DocumentConversionJobDto> createJob(@RequestBody DocumentConversionJobRequestDto jobRequestDto) {
        DocumentConversionJobDto createdJob = jobService.createJob(jobRequestDto);
        return ResponseEntity.ok(createdJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentConversionJobDto> updateJob(@PathVariable Long id,
                                                              @RequestBody DocumentConversionJobRequestDto jobRequestDto) {
        DocumentConversionJobDto updatedJob = jobService.updateJob(id, jobRequestDto);
        return ResponseEntity.ok(updatedJob);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    // Fallback method for circuit breaker
    private ResponseEntity<DocumentConversionJobDto> fallbackGetJobById(Long id, Throwable t) {
        DocumentConversionJobDto fallbackDto = ConversionFallbackUtil.fallbackJob(id, t);
        return ResponseEntity.ok(fallbackDto);
    }
}