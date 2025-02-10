package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.ImageTransformationStrategy;
import org.springframework.stereotype.Component;

@Component
public class GrayscaleTransformationStrategy implements ImageTransformationStrategy {

    @Override
    public String transform(String originalImage) {
        // Simulate grayscale transformation.
        return "Grayscale Transformation Applied: " + originalImage;
    }
}