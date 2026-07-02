package com.pratyaksh.omnidocs_ai.ai.embedding.generator;

import com.pratyaksh.omnidocs_ai.document.entity.Document;

public interface EmbeddingGenerationService {

  void generate(Document document);

}