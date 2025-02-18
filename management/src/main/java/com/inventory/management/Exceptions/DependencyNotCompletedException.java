package com.inventory.management.Exceptions;

public class DependencyNotCompletedException extends RuntimeException {
    public DependencyNotCompletedException(String message) {
        super(message);
    }
}
