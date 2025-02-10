package com.inventory.management.Dtos;

import com.inventory.management.Enums.TransformationStatus;
import com.inventory.management.Enums.TransformationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageTransformationJobDto {
    private Long id;
    private String imageName;
    private TransformationType transformationType;
    private String originalImage;
    private String transformedImage;
    private TransformationStatus transformationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}