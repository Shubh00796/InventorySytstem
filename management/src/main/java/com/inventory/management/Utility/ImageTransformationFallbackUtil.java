package com.inventory.management.Utility;

import com.inventory.management.Dtos.ImageTransformationJobDto;
import com.inventory.management.Enums.TransformationStatus;
import com.inventory.management.Enums.TransformationType;

import java.time.LocalDateTime;

public class ImageTransformationFallbackUtil {

    public static ImageTransformationJobDto fallbackJob(Long id, Throwable t) {
        return ImageTransformationJobDto.builder()
                .id(id)
                .imageName("Fallback Image")
                .transformationType(TransformationType.GRAYSCALE)
                .originalImage("N/A")
                .transformedImage("Fallback transformation applied due to: " + t.getMessage())
                .transformationStatus(TransformationStatus.FAILED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}