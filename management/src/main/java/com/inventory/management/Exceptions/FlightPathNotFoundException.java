package com.inventory.management.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FlightPathNotFoundException extends RuntimeException {
    public FlightPathNotFoundException(String message) {
        super(message);
    }
}