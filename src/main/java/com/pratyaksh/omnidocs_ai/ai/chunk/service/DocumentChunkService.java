package com.pratyaksh.omnidocs_ai.ai.chunk.service;

import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;

public interface DocumentChunkService {

  void saveChunks(ProcessingContext context);

}