package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.Enums.TransformationType;
import com.inventory.management.FactoryInterfaces.ImageTransformationStrategy;
import com.inventory.management.FactoryInterfaces.ImageTransformationStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageTransformationStrategyFactoryImpl implements ImageTransformationStrategyFactory {

    private final GrayscaleTransformationStrategy grayscaleStrategy;
    private final SepiaTransformationStrategy sepiaStrategy;
    private final InvertTransformationStrategy invertStrategy;

    @Autowired
    public ImageTransformationStrategyFactoryImpl(GrayscaleTransformationStrategy grayscaleStrategy,
                                                  SepiaTransformationStrategy sepiaStrategy,
                                                  InvertTransformationStrategy invertStrategy) {
        this.grayscaleStrategy = grayscaleStrategy;
        this.sepiaStrategy = sepiaStrategy;
        this.invertStrategy = invertStrategy;
    }

    @Override
    public ImageTransformationStrategy getTransformationStrategy(TransformationType type) {
        switch (type) {
            case GRAYSCALE:
                return grayscaleStrategy;
            case SEPIA:
                return sepiaStrategy;
            case INVERT:
                return invertStrategy;
            default:
                throw new IllegalArgumentException("Unsupported transformation type: " + type);
        }
    }
}