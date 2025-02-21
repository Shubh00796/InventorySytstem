package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.ReportDTO;
import com.inventory.management.Enums.ReportType;
import com.inventory.management.Exceptions.InvalidStateTransitionException;
import com.inventory.management.Mapper.ReportMapper;
import com.inventory.management.Model.Report;
import com.inventory.management.ReposiotryServices.ReportReposiotryService;
import com.inventory.management.events.ReportEventPublisher;
import com.inventory.management.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final ReportReposiotryService reposiotryService;
    private final ReportMapper mapper;
    private final ReportEventPublisher eventPublisher;

    @Override
    public ReportDTO generateReport(ReportDTO reportDTO) {
        Report entity = mapper.toEntity(reportDTO);
        entity.setGeneratedAt(LocalDateTime.now());
        Report report = reposiotryService.saveReport(entity);


        ReportType nextStatus = entity.getReportType().getNextStatus();
        if (nextStatus != null) {
            eventPublisher.publishStateChangeEvent(entity.getId(), entity.getReportType(), nextStatus);
        }

        return mapper.toDto(report);
    }

    @Override
    public Optional<ReportDTO> getReportById(Long id) {
        return Optional.ofNullable(reposiotryService.findByReportId(id))
                .map(mapper::toDto);
    }

    @Override
    public List<ReportDTO> getAllReports() {
        return reposiotryService.findAllReports()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByType(ReportType reportType) {
        return reposiotryService.getReportsByType(reportType)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO updateReport(ReportDTO reportDTO) throws InvalidStateTransitionException {
        log.info("Updating report with ID: {}", reportDTO.getId());

        Report existingReport = getReportEntity(reportDTO.getId());
        log.info("Existing Report: {}", existingReport);

        if (existingReport == null) {
            throw new IllegalArgumentException("Report with ID " + reportDTO.getId() + " not found.");
        }

        log.info("Existing Report Type: {}", existingReport.getReportType());

        ReportType newStatus;
        try {
            newStatus = ReportType.valueOf(reportDTO.getReportType());
        } catch (IllegalArgumentException e) {
            log.error("Invalid Report Type: {}", reportDTO.getReportType());
            throw new IllegalArgumentException("Invalid Report Type: " + reportDTO.getReportType());
        }

        log.info("Transitioning from {} to {}", existingReport.getReportType(), newStatus);

        validateStateTransition(existingReport.getReportType(), newStatus);

        mapper.updateEntityFromDto(reportDTO, existingReport);
        Report updatedReport = reposiotryService.saveReport(existingReport);

        log.info("Successfully updated report ID: {}", updatedReport.getId());
        return mapper.toDto(updatedReport);
    }

    private void validateStateTransition(ReportType oldStatus, ReportType newStatus) throws InvalidStateTransitionException {
        if (oldStatus == null) {
            throw new RuntimeException("Current report type is null. Cannot validate transition.");
        }
        if (newStatus == null || !oldStatus.canTransitionTo(newStatus)) {
            throw new InvalidStateTransitionException(oldStatus, newStatus);
        }
    }


    private Report getReportEntity(Long reportId) {
        return reposiotryService.findByReportId(reportId);
    }

    @Override
    public void deleteReport(Long id) {

        reposiotryService.deleteReport(id);
    }
}