package com.pratyaksh.omnidocs_ai.document.response;

import com.pratyaksh.omnidocs_ai.document.entity.DocumentStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentSummaryResponse {

  private UUID documentUuid;

  private String originalFileName;

  private String contentType;

  private Long fileSize;

  private DocumentStatus status;

  private LocalDateTime createdAt;

  private UUID workspaceUuid;

  private String workspaceName;

}