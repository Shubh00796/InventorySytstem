package com.inventory.management.FactoryInterfaces;

import com.inventory.management.Enums.DocumentType;
import com.inventory.management.FactoryInterfaceImpl.LocalDocDocumentConverter;
import com.inventory.management.FactoryInterfaceImpl.LocalPdfDocumentConverter;
import com.inventory.management.FactoryInterfaceImpl.LocalTxtDocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalDocumentConverterFactory implements DocumentConverterFactory {

    private final LocalPdfDocumentConverter pdfConverter;
    private final LocalDocDocumentConverter docConverter;
    private final LocalTxtDocumentConverter txtConverter;

    @Autowired
    public LocalDocumentConverterFactory(LocalPdfDocumentConverter pdfConverter,
                                         LocalDocDocumentConverter docConverter,
                                         LocalTxtDocumentConverter txtConverter) {
        this.pdfConverter = pdfConverter;
        this.docConverter = docConverter;
        this.txtConverter = txtConverter;
    }

    @Override
    public DocumentConverter getDocumentConverter(DocumentType documentType) {
        switch (documentType) {
            case PDF:
                return pdfConverter;
            case DOC:
                return docConverter;
            case TXT:
                return txtConverter;
            default:
                throw new IllegalArgumentException("Unsupported document type: " + documentType);
        }
    }
}