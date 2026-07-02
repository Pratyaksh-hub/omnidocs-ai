package com.pratyaksh.omnidocs_ai.ai.event;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentUploadedEvent {

  private UUID documentUuid;

}