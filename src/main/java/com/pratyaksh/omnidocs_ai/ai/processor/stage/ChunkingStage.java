package com.pratyaksh.omnidocs_ai.ai.processor.stage;

import com.pratyaksh.omnidocs_ai.ai.chunk.splitter.TextSplitter;
import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class ChunkingStage implements ProcessingStage {

  private final TextSplitter textSplitter;

  @Override
  public void process(ProcessingContext context) {

    context.setChunks(
        textSplitter.split(
            context.getExtractedText()
        )
    );
  }
}