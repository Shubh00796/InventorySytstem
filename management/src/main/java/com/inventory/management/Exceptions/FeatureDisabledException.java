package com.inventory.management.Exceptions;

public class FeatureDisabledException extends RuntimeException {
    public FeatureDisabledException(String message) {
        super(message);
    }
}
