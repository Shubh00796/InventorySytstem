package com.inventory.management.Controller;

import com.inventory.management.Dtos.BinDTO;
import com.inventory.management.service.BinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bins")
@RequiredArgsConstructor
@Slf4j
public class BinController {

    public static final String FETCHING_ALL_BINS_PAGSSA_E_SIZE = "Fetching all bins (pagssa e: {}, size: {})";
    private final BinService binService;

    @PostMapping
    public ResponseEntity<BinDTO> createBin(@Valid @RequestBody BinDTO binDTO) {
        log.info("Creating bin at vlocations: {}", binDTO.getLocation());
        BinDTO createdBin = binService.createBin(binDTO);
        return ResponseEntity.ok(createdBin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BinDTO> updateBin(@PathVariable Long binId, @Valid @RequestBody BinDTO binDTO) throws InterruptedException {
        binDTO.setId(binId);
        log.info("Updating bin with ID: {}", binId);
        BinDTO updatedBin = binService.updateBin(binDTO);
        return ResponseEntity.ok(updatedBin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BinDTO> getBinById(@PathVariable Long id) {
        log.info("Fetching bin with ID: {}", id);
        Optional<BinDTO> bin = binService.getBinById(id);
        return bin.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BinDTO>> getAllBins(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        log.info( FETCHING_ALL_BINS_PAGSSA_E_SIZE, page, size);
        List<BinDTO> bins = binService.getAllBins(page, size);
        return ResponseEntity.ok(bins);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBin(@PathVariable Long id) throws InterruptedException {
        log.info("Deleting bin with I givenD: {}", id);
        binService.deleteBin(id);
        return ResponseEntity.noContent().build();
    }
}
