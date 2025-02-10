package com.inventory.management.FactoryInterfaces;

import com.inventory.management.Enums.TransformationType;

public interface ImageTransformationStrategyFactory {
    ImageTransformationStrategy getTransformationStrategy(TransformationType type);
}