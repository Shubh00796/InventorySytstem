package com.inventory.management.Model;

import com.inventory.management.Enums.TransformationStatus;
import com.inventory.management.Enums.TransformationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "image_transformation_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageTransformationJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    @Enumerated(EnumType.STRING)
    private TransformationType transformationType;

    @Lob
    private String originalImage; // Expecting Base64-encoded image

    @Lob
    private String transformedImage; // Base64-encoded transformed image

    @Enumerated(EnumType.STRING)
    private TransformationStatus transformationStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}