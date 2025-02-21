package com.inventory.management.ReposiotryServices;

import com.inventory.management.Enums.ReportType;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Report;
import com.inventory.management.Repo.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class ReportReposiotryService {
    private final ReportRepository reportRepository;
    private final ProjectRepositoryService projectRepositoryService;


    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }

    public Report findByReportId(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(()-> new ResourceNotFoundException("Id with given report not found" + reportId));
    }

    public Report saveReport(Report report) {
        if (report.getProjectId() == null || !projectRepositoryService.existsById(report.getProjectId())) {
            throw new ResourceNotFoundException("Cannot create issue. Project with ID " + report.getProjectId() + " does not exist.");
        }

        return reportRepository.save(report);
    }

    public List<Report> getReportsByType(ReportType reportType) {
        return reportRepository.findByReportType(reportType);
    }

    public void deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("The report with given id didnt found" + reportId));
        reportRepository.delete(report);
    }
}
