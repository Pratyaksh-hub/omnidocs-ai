package com.pratyaksh.omnidocs_ai.document.dto;

import java.io.InputStream;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadDocumentRequest {

  private UUID workspaceUuid;

  private InputStream inputStream;

  private String originalFilename;

  private String contentType;

  private long fileSize;

}