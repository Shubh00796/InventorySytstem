package com.inventory.management.Repo;


import com.inventory.management.Enums.ReportType;
import com.inventory.management.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReportType(ReportType reportType);
}
