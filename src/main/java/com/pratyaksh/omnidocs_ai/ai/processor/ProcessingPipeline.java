package com.pratyaksh.omnidocs_ai.ai.processor;

import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;
import com.pratyaksh.omnidocs_ai.ai.processor.stage.ProcessingStage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessingPipeline {

  private final List<ProcessingStage> stages;

  public void execute(ProcessingContext context) {

    for (ProcessingStage stage : stages) {
      stage.process(context);
    }
  }
}