package com.pratyaksh.omnidocs_ai.ai.embedding.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmbeddingResponse {

  private float[] embedding;

}