package com.inventory.management.Controller;

import com.inventory.management.Dtos.CurrencyConversionJobDto;
import com.inventory.management.Dtos.CurrencyConversionJobRequestDto;
import com.inventory.management.Utility.CurrencyConversionFallbackUtil;
import com.inventory.management.service.CurrencyConversionJobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversions")
@RequiredArgsConstructor
public class CurrencyConversionJobController {

    private final CurrencyConversionJobService conversionService;

    @GetMapping
    public ResponseEntity<List<CurrencyConversionJobDto>> getAllConversions() {
        List<CurrencyConversionJobDto> jobs = conversionService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "currencyService", fallbackMethod = "fallbackGetConversion")
    public ResponseEntity<CurrencyConversionJobDto> getConversionById(@PathVariable Long id) {
        CurrencyConversionJobDto jobDto = conversionService.getJobById(id);
        return ResponseEntity.ok(jobDto);
    }

    @PostMapping
    @RateLimiter(name = "currencyService")
    public ResponseEntity<CurrencyConversionJobDto> createConversion(@RequestBody CurrencyConversionJobRequestDto requestDto) {
        CurrencyConversionJobDto createdJob = conversionService.createJob(requestDto);
        return ResponseEntity.ok(createdJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyConversionJobDto> updateConversion(@PathVariable Long id,
                                                                     @RequestBody CurrencyConversionJobRequestDto requestDto) {
        CurrencyConversionJobDto updatedJob = conversionService.updateJob(id, requestDto);
        return ResponseEntity.ok(updatedJob);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversion(@PathVariable Long id) {
        conversionService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<CurrencyConversionJobDto> fallbackGetConversion(Long id, Throwable t) {
        CurrencyConversionJobDto fallbackDto = CurrencyConversionFallbackUtil.fallbackJob(id, t);
        return ResponseEntity.ok(fallbackDto);
    }
}