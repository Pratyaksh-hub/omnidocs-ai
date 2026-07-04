package com.pratyaksh.omnidocs_ai.ai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmbeddingRequest {

  private Content content;

}