package com.inventory.management.Dtos;

import com.inventory.management.Enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentConversionJobRequestDto {

    private String documentName;
    private DocumentType documentType;
    private String sourceContent;
}
