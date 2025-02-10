// src/main/java/com/inventory/management/FactoryInterfaceImpl/LocalPdfDocumentConverter.java
package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.FactoryInterfaces.DocumentConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class LocalPdfDocumentConverter implements DocumentConverter {

    @Override
    public String convert(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 700);
                contentStream.showText(content);
                contentStream.endText();
            }

            document.save(outputStream);

            byte[] pdfBytes = outputStream.toByteArray();
            return "PDF Converted Content: " + java.util.Base64.getEncoder().encodeToString(pdfBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error during PDF conversion", e);
        }
    }
}
