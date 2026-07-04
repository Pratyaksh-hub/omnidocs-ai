package com.pratyaksh.omnidocs_ai.ai.embedding.generator;

import com.pratyaksh.omnidocs_ai.ai.chunk.entity.DocumentChunk;
import com.pratyaksh.omnidocs_ai.ai.chunk.repository.DocumentChunkRepository;
import com.pratyaksh.omnidocs_ai.ai.embedding.entity.DocumentEmbedding;
import com.pratyaksh.omnidocs_ai.ai.embedding.provider.EmbeddingProvider;
import com.pratyaksh.omnidocs_ai.ai.embedding.repository.DocumentEmbeddingRepository;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmbeddingGenerationServiceImpl
    implements EmbeddingGenerationService {

  private static final String PROVIDER = "GEMINI";
  private static final String MODEL = "gemini-embedding-001";

  private final DocumentChunkRepository chunkRepository;
  private final DocumentEmbeddingRepository embeddingRepository;
  private final EmbeddingProvider embeddingProvider;

  @Override
  public void generate(Document document) {

    List<DocumentChunk> chunks =
        chunkRepository.findByDocument(document);

    log.info("Generating embeddings for {} chunks",
        chunks.size());

    for (DocumentChunk chunk : chunks) {

      float[] vector =
          embeddingProvider.generateEmbedding(
              chunk.getContent()
          );

      DocumentEmbedding embedding =
          DocumentEmbedding.create(
              chunk,
              PROVIDER,
              MODEL,
              vector
          );

      embeddingRepository.save(embedding);

      log.info(
          "Embedding generated for chunk {}",
          chunk.getChunkIndex()
      );
    }

    log.info(
        "Embedding generation completed for document {}",
        document.getUuid()
    );
  }
}