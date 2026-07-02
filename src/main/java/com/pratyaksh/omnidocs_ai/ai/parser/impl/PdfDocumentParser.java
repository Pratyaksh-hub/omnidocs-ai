package com.pratyaksh.omnidocs_ai.ai.parser.impl;

import com.pratyaksh.omnidocs_ai.ai.parser.DocumentParser;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.storage.service.StorageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfDocumentParser implements DocumentParser {

  private static final String PDF_CONTENT_TYPE = "application/pdf";

  private final StorageService storageService;

  @Override
  public boolean supports(String contentType) {
    return PDF_CONTENT_TYPE.equalsIgnoreCase(contentType);
  }

  @Override
  public String extractText(Document document) {

    Resource resource = storageService.load(
        document.getStoredFile().getStoredFileName()
    );

    try (
        PDDocument pdfDocument = Loader.loadPDF(
            resource.getInputStream().readAllBytes()
        )
    ) {
      return new PDFTextStripper().getText(pdfDocument);

    } catch (IOException ex) {
      throw new RuntimeException("Failed to extract text from PDF.", ex);
    }
  }
}