package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.DocumentConverter;
import org.springframework.stereotype.Component;

@Component
public class LocalDocDocumentConverter implements DocumentConverter {
    @Override
    public String convert(String content) {
        // Simulate DOC conversion process
        return "DOC Converted Content: " + content.toLowerCase();
    }
}