package com.pratyaksh.omnidocs_ai.ai.processor.stage;

import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;

public interface ProcessingStage {

  void process(ProcessingContext context);

}