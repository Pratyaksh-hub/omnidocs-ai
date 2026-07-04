package com.pratyaksh.omnidocs_ai.ai.dto.embedding;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeminiEmbeddingResponse {

  private Embedding embedding;

  @Getter
  @Setter
  public static class Embedding {

    private List<Float> values;

  }
}