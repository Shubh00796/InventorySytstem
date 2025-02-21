package com.inventory.management.events;

import com.inventory.management.Model.Report;
import com.inventory.management.Repo.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportEventListener {

    private final ReportRepository reportRepository;

    @EventListener
    public void handleReportGenerated(ReportGeneratedEvent event) {
        Optional<Report> optionalReport = reportRepository.findById(event.getReportId());

        optionalReport.ifPresent(report -> {
            if (event.getNewStatus() != null) {
                report.setReportType(event.getNewStatus());
                reportRepository.save(report);
                log.info("Report ID={} status changed from {} to {}", report.getId(), event.getOldStatus(), event.getNewStatus());
            } else {
                log.info("Report ID={} is in FINAL status: {}", report.getId(), report.getReportType());
            }
        });
    }
}