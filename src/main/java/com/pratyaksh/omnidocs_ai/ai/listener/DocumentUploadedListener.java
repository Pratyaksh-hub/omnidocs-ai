package com.pratyaksh.omnidocs_ai.ai.listener;

import com.pratyaksh.omnidocs_ai.ai.event.DocumentUploadedEvent;
import com.pratyaksh.omnidocs_ai.ai.processing.DocumentProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentUploadedListener {

  private final DocumentProcessingService processingService;

  @EventListener
  public void onDocumentUploaded(DocumentUploadedEvent event) {

    processingService.process(
        event.getDocumentUuid()
    );
  }
}