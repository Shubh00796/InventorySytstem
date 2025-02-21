package com.inventory.management.Model;

import com.inventory.management.Enums.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;


    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String name;
    private String description;

    @Column(columnDefinition = "TEXT")
    private String reportData;

    private LocalDateTime generatedAt;

    @Version
    private Integer version;
}
