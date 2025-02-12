package com.inventory.management.ValidatorLogics;

import com.inventory.management.Dtos.QuoteDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;

import java.util.Objects;

public final class QuoteValidator {

    private QuoteValidator() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static void validate(QuoteDTO quoteDTO) {
        Objects.requireNonNull(quoteDTO, "QuoteDTO must not be null");
        Objects.requireNonNull(quoteDTO.getContent(), "Quote content must not be null");
        Objects.requireNonNull(quoteDTO.getAuthor(), "Quote author must not be null");

        if (quoteDTO.getContent().trim().isEmpty()) {
            throw new ResourceNotFoundException("Quote content must not be empty");
        }
        if (quoteDTO.getAuthor().trim().isEmpty()) {
            throw new ResourceNotFoundException("Quote author must not be empty");
        }
    }
}