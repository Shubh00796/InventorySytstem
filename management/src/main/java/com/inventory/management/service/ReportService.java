package com.inventory.management.service;

import com.inventory.management.Dtos.ReportDTO;
import com.inventory.management.Enums.ReportType;
import com.inventory.management.Exceptions.InvalidStateTransitionException;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    ReportDTO generateReport(ReportDTO reportDTO);

    Optional<ReportDTO> getReportById(Long id);

    List<ReportDTO> getAllReports();

    List<ReportDTO> getReportsByType(ReportType reportType);

    ReportDTO updateReport(ReportDTO reportDTO) throws InvalidStateTransitionException;

    void deleteReport(Long id);
}
