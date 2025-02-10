package com.inventory.management.Dtos;

import com.inventory.management.Enums.ConversionStatus;
import com.inventory.management.Enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentConversionJobDto {
    private Long id;
    private String documentName;
    private DocumentType documentType;
    private String sourceContent;
    private String convertedContent;
    private ConversionStatus conversionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
