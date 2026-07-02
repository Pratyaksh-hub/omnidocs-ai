package com.pratyaksh.omnidocs_ai.ai.processing;

import com.pratyaksh.omnidocs_ai.ai.embedding.generator.EmbeddingGenerationService;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.service.query.DocumentQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentProcessingServiceImpl
    implements DocumentProcessingService {

  private final DocumentQueryService documentQueryService;
  private final EmbeddingGenerationService embeddingGenerationService;

  @Override
  public void process(UUID documentUuid) {

    log.info("Processing document [{}]", documentUuid);

    Document document =
        documentQueryService.getEntity(documentUuid);

    embeddingGenerationService.generate(document);

    log.info("Finished processing document [{}]", documentUuid);
  }
}