package com.inventory.management.Exceptions;

public class MaintenanceRequestNotFoundException extends RuntimeException {
    public MaintenanceRequestNotFoundException(String message) {
        super(message);
    }
}
