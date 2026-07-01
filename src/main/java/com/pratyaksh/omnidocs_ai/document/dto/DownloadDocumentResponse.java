package com.pratyaksh.omnidocs_ai.document.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@Builder
public class DownloadDocumentResponse {

  private Resource resource;

  private String originalFileName;

  private String contentType;

  private Long fileSize;

}