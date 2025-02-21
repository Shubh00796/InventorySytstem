package com.inventory.management.Controller;

import com.inventory.management.Dtos.ReportDTO;
import com.inventory.management.Enums.ReportType;
import com.inventory.management.Exceptions.InvalidStateTransitionException;
import com.inventory.management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportDTO> generateReport(@RequestBody ReportDTO reportDTO) {
        return ResponseEntity.ok(reportService.generateReport(reportDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        Optional<ReportDTO> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/type/{reportType}")
    public ResponseEntity<List<ReportDTO>> getReportsByType(@PathVariable ReportType reportType) {
        return ResponseEntity.ok(reportService.getReportsByType(reportType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) throws InvalidStateTransitionException {
        reportDTO.setId(id);
        return ResponseEntity.ok(reportService.updateReport(reportDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
