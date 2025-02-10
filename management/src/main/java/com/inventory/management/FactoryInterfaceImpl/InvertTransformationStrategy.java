package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.ImageTransformationStrategy;
import org.springframework.stereotype.Component;

@Component
public class InvertTransformationStrategy implements ImageTransformationStrategy {

    @Override
    public String transform(String originalImage) {
        return "Invert Transformation Applied: " + originalImage;
    }
}