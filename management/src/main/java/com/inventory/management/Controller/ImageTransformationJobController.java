package com.inventory.management.Controller;

import com.inventory.management.Dtos.ImageTransformationJobDto;
import com.inventory.management.Dtos.ImageTransformationJobRequestDto;
import com.inventory.management.Utility.ImageTransformationFallbackUtil;
import com.inventory.management.service.ImageTransformationJobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageTransformationJobController {

    private final ImageTransformationJobService jobService;

    @GetMapping
    public ResponseEntity<List<ImageTransformationJobDto>> getAllJobs() {
        List<ImageTransformationJobDto> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "imageService", fallbackMethod = "fallbackGetJobById")
    public ResponseEntity<ImageTransformationJobDto> getJobById(@PathVariable Long id) {
        ImageTransformationJobDto jobDto = jobService.getJobById(id);
        return ResponseEntity.ok(jobDto);
    }

    @PostMapping
    @RateLimiter(name = "imageService")
    public ResponseEntity<ImageTransformationJobDto> createJob(@RequestBody ImageTransformationJobRequestDto requestDto) {
        ImageTransformationJobDto createdJob = jobService.createJob(requestDto);
        return ResponseEntity.ok(createdJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageTransformationJobDto> updateJob(@PathVariable Long id,
                                                               @RequestBody ImageTransformationJobRequestDto requestDto) {
        ImageTransformationJobDto updatedJob = jobService.updateJob(id, requestDto);
        return ResponseEntity.ok(updatedJob);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<ImageTransformationJobDto> fallbackGetJobById(Long id, Throwable t) {
        ImageTransformationJobDto fallbackDto = ImageTransformationFallbackUtil.fallbackJob(id, t);
        return ResponseEntity.ok(fallbackDto);
    }
}