package com.inventory.management;

import com.inventory.management.Dtos.InventoryItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryItemServiceTests {

    @Autowired
    private WebTestClient webTestClient; // Spring Boot injects this for you

    private static final String URL = "/api/inventory";

    @Test
    public void testCircuitBreakerFallback() {
        for (int i = 0; i < 5; i++) {
            webTestClient.post()
                    .uri(URL)
                    .bodyValue(new InventoryItemDTO())  // Replace with actual DTO
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        webTestClient.post()
                .uri(URL)
                .bodyValue(new InventoryItemDTO())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testRateLimiter() {
        for (int i = 0; i < 6; i++) {
            webTestClient.get()
                    .uri(URL + "/active")
                    .exchange()
                    .expectStatus().isEqualTo(i < 5 ? HttpStatus.OK : HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
