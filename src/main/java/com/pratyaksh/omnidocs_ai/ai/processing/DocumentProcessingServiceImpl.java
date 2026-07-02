package com.pratyaksh.omnidocs_ai.ai.processing;

import com.pratyaksh.omnidocs_ai.ai.chunk.model.TextChunk;
import com.pratyaksh.omnidocs_ai.ai.chunk.service.DocumentChunkService;
import com.pratyaksh.omnidocs_ai.ai.chunk.splitter.TextSplitter;
import com.pratyaksh.omnidocs_ai.ai.embedding.generator.EmbeddingGenerationService;
import com.pratyaksh.omnidocs_ai.ai.parser.DocumentParser;
import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.service.query.DocumentQueryService;
import java.util.List;
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
  private final DocumentParser documentParser;
  private final DocumentChunkService documentChunkService;
  private final TextSplitter textSplitter;

  @Override
  public void process(UUID documentUuid) {

    log.info("Processing document [{}]", documentUuid);

    Document document =
        documentQueryService.getEntity(documentUuid);

    log.info("Extracting text...");
    String extractText = documentParser.extractText(document);
    log.info("Text extraction completed");

    log.info("Chunking document...");
    List<TextChunk> chunks = textSplitter.split(extractText);
    int chunkSize = chunks.size();
    log.info("Chunking document completed");

    log.info("Saving " + chunkSize + " chunks...");
    documentChunkService.saveChunks(ProcessingContext.builder()
        .document(document).extractedText(extractText).chunks(chunks).build());
    log.info("Saved " + chunkSize + " chunks");

    log.info("Generating embeddings for " + chunkSize + " chunks");
    embeddingGenerationService.generate(document);
    log.info("Embeddings generated for " + chunkSize + " chunks");

    log.info("Finished processing document [{}]", documentUuid);
  }
}