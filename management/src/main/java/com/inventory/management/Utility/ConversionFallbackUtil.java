package com.inventory.management.Utility;

import com.inventory.management.Dtos.DocumentConversionJobDto;
import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.DocumentType;

import java.time.LocalDateTime;

public class ConversionFallbackUtil {

    public static DocumentConversionJobDto fallbackJob(Long id, Throwable t) {
        return DocumentConversionJobDto.builder()
                .id(id)
                .documentName("Fallback Document")
                .documentType(DocumentType.TXT) // Default fallback type
                .sourceContent("N/A")
                .convertedContent("Fallback conversion in effect due to: " + t.getMessage())
                .conversionStatus(ConversionStatus.FAILED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}