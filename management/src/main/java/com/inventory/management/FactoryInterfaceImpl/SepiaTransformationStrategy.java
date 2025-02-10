package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.ImageTransformationStrategy;
import org.springframework.stereotype.Component;

@Component
public class SepiaTransformationStrategy implements ImageTransformationStrategy {

    @Override
    public String transform(String originalImage) {
        // Simulate sepia transformation.
        return "Sepia Transformation Applied: " + originalImage;
    }
}