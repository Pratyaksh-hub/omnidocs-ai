package com.pratyaksh.omnidocs_ai.ai.embedding.service;

import com.pratyaksh.omnidocs_ai.ai.embedding.provider.EmbeddingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

  private final EmbeddingProvider embeddingProvider;

  @Override
  public float[] generate(String text) {
    return embeddingProvider.generateEmbedding(text);
  }
}