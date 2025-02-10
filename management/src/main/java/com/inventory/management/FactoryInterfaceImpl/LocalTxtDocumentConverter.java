package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.DocumentConverter;
import org.springframework.stereotype.Component;

@Component
public class LocalTxtDocumentConverter implements DocumentConverter {
    @Override
    public String convert(String content) {
        // Simulate TXT conversion process (e.g., reversing the content)
        return "TXT Converted Content: " + new StringBuilder(content).reverse().toString();
    }
}