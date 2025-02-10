package com.inventory.management.Model;

import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_conversion_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentConversionJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentName;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Lob
    private String sourceContent;

    @Lob
    private String convertedContent;

    @Enumerated(EnumType.STRING)
    private ConversionStatus conversionStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}