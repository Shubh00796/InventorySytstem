package com.inventory.management.Dtos;

import com.inventory.management.Enums.TransformationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageTransformationJobRequestDto {
    private String imageName;
    private TransformationType transformationType;
    private String originalImage;
}
