package com.pratyaksh.omnidocs_ai.ai.embedding.provider;

public interface EmbeddingProvider {

  float[] generateEmbedding(String text);

}