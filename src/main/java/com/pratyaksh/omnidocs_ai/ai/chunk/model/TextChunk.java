package com.pratyaksh.omnidocs_ai.ai.chunk.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TextChunk {

  private Integer chunkIndex;

  private String content;

  private Integer startOffset;

  private Integer endOffset;

  private Integer characterCount;

  private String documentUuid;

}