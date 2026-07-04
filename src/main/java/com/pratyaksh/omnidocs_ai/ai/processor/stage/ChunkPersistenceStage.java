package com.pratyaksh.omnidocs_ai.ai.processor.stage;

import com.pratyaksh.omnidocs_ai.ai.chunk.service.DocumentChunkService;
import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class ChunkPersistenceStage implements ProcessingStage {

  private final DocumentChunkService documentChunkService;

  @Override
  public void process(ProcessingContext context) {

    documentChunkService.saveChunks(context);

  }
}