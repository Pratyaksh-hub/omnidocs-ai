package com.pratyaksh.omnidocs_ai.ai.processor.model;

import com.pratyaksh.omnidocs_ai.ai.chunk.model.TextChunk;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProcessingContext {

  private Document document;

  private String extractedText;

  private List<TextChunk> chunks;

}