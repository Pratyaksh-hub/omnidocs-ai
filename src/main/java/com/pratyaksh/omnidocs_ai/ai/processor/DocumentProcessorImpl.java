package com.pratyaksh.omnidocs_ai.ai.processor;

import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.entity.DocumentStatus;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentNotFoundException;
import com.pratyaksh.omnidocs_ai.document.repository.DocumentRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentProcessorImpl implements DocumentProcessor {

  private final DocumentRepository documentRepository;
  private final ProcessingPipeline processingPipeline;

  @Override
  public void process(UUID documentUuid) {

    Document document = documentRepository
        .findByUuidAndDeletedFalse(documentUuid)
        .orElseThrow(() -> new DocumentNotFoundException(documentUuid));

    document.markProcessing();

    ProcessingContext context = ProcessingContext.builder()
        .document(document)
        .build();

    processingPipeline.execute(context);

    log.info("Extracted {} chunks.", context.getChunks().size());

    context.getChunks().stream()
        .limit(5)
        .forEach(chunk ->
            log.info(
                "Chunk {} | Characters={} | Start={} | End={} | Content={}",
                chunk.getChunkIndex(),
                chunk.getCharacterCount(),
                chunk.getStartOffset(),
                chunk.getEndOffset(),
                chunk.getContent()
            )
        );

    document.markReady();
  }
}