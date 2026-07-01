package com.pratyaksh.omnidocs_ai.storage.service;

import com.pratyaksh.omnidocs_ai.storage.model.StorageRequest;
import com.pratyaksh.omnidocs_ai.storage.model.StoredFileMetadata;
import org.springframework.core.io.Resource;

public interface StorageService {

  StoredFileMetadata store(StorageRequest request);

  Resource load(String storagePath);

  void delete(String storagePath);

  boolean exists(String storagePath);

}