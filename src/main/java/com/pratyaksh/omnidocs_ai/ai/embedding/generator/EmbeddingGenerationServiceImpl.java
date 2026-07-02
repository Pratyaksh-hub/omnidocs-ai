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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmbeddingGenerationServiceImpl
    implements EmbeddingGenerationService {

  private static final String PROVIDER = "OPENAI";
  private static final String MODEL = "text-embedding-3-small";

  private final DocumentChunkRepository chunkRepository;
  private final DocumentEmbeddingRepository embeddingRepository;
  private final EmbeddingProvider embeddingProvider;

  @Override
  public void generate(Document document) {

    var chunks = chunkRepository.findByDocument(document);

    for (DocumentChunk chunk : chunks) {

      float[] embedding =
          embeddingProvider.generateEmbedding(chunk.getContent());

      embeddingRepository.save(
          DocumentEmbedding.create(
              chunk,
              PROVIDER,
              MODEL,
              embedding
          )
      );

      log.info(
          "Embedding generated for chunk {}",
          chunk.getChunkIndex()
      );
    }
  }
}