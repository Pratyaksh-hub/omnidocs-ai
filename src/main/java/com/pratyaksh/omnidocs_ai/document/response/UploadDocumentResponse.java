package com.pratyaksh.omnidocs_ai.document.response;

import com.pratyaksh.omnidocs_ai.document.entity.DocumentStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UploadDocumentResponse {

  private UUID documentUuid;

  private DocumentStatus status;

  private String originalFileName;

}