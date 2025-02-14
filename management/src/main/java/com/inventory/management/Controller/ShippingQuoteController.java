package com.inventory.management.Controller;

import com.inventory.management.Dtos.ShippingQuoteDTO;
import com.inventory.management.service.ShippingQuoteService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipping-quotes")
@RequiredArgsConstructor
public class ShippingQuoteController {

    private final ShippingQuoteService quoteService;

    @GetMapping
    @CircuitBreaker(name = "shippingQuoteService", fallbackMethod = "getAllFallback")
    @RateLimiter(name = "shippingQuoteService", fallbackMethod = "getAllFallback")
    public ResponseEntity<List<ShippingQuoteDTO>> getAllQuotes() {
        return ResponseEntity.ok(quoteService.getAllQuotes());
    }

    private ResponseEntity<List<ShippingQuoteDTO>> getAllFallback(Throwable t) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingQuoteDTO> getQuoteById(@PathVariable Long id) {
        Optional<ShippingQuoteDTO> quote = quoteService.getQuoteById(id);
        return quote.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShippingQuoteDTO> createQuote(@RequestBody ShippingQuoteDTO quoteDTO) {
        return new ResponseEntity<>(quoteService.createQuote(quoteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingQuoteDTO> updateQuote(@PathVariable Long id, @RequestBody ShippingQuoteDTO quoteDTO) {
        return ResponseEntity.ok(quoteService.updateQuote(id, quoteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.noContent().build();
    }
}
