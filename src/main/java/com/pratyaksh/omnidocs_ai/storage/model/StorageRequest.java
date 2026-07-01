package com.pratyaksh.omnidocs_ai.storage.model;

import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StorageRequest {

  private final InputStream inputStream;

  private final String originalFilename;

  private final long fileSize;

}