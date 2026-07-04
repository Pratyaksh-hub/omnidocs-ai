package com.pratyaksh.omnidocs_ai.ai.controller;

import com.pratyaksh.omnidocs_ai.ai.processor.DocumentProcessor;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/documents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DocumentProcessingController {

  private final DocumentProcessor documentProcessor;

  @PostMapping("/{documentUuid}/process")
  public ResponseEntity<Void> process(
      @PathVariable UUID documentUuid) {

    documentProcessor.process(documentUuid);

    return ResponseEntity.accepted().build();
  }
}