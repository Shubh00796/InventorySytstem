package com.inventory.management.FactoryInterfaces;

import com.inventory.management.Enums.DocumentType;

public interface DocumentConverterFactory {
    DocumentConverter getDocumentConverter(DocumentType documentType);
}