package com.pratyaksh.omnidocs_ai.storage.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoredFileMetadata {

  private final String storedFileName;

  private final String storagePath;

  private final String checksum;

  private final long fileSize;

}