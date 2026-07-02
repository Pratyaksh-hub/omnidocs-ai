package com.pratyaksh.omnidocs_ai.ai.embedding.provider.openai;

import com.pratyaksh.omnidocs_ai.ai.embedding.provider.EmbeddingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiEmbeddingProvider implements EmbeddingProvider {

  private final EmbeddingModel embeddingModel;

  @Override
  public float[] generateEmbedding(String text) {

    return embeddingModel
        .embed(new Document(text));
  }
}