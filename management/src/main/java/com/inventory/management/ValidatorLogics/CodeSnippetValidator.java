package com.inventory.management.ValidatorLogics;

import com.inventory.management.Dtos.CodeSnippetDTO;

import java.util.Objects;

public final class CodeSnippetValidator {

    private CodeSnippetValidator() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static void validate(CodeSnippetDTO codeSnippetDTO) {
        Objects.requireNonNull(codeSnippetDTO, "CodeSnippetDTO must not be null");

        if (isBlank(codeSnippetDTO.getTitle())) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        if (isBlank(codeSnippetDTO.getLanguage())) {
            throw new IllegalArgumentException("Language must not be empty");
        }
        if (isBlank(codeSnippetDTO.getCode())) {
            throw new IllegalArgumentException("Code content must not be empty");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}