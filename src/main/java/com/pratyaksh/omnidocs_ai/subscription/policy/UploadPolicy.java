package com.pratyaksh.omnidocs_ai.subscription.policy;

import java.util.Set;

public interface UploadPolicy {

  Set<String> allowedContentTypes();

  long maxFileSize();

  long maxWorkspaceCount();

  long maxDocumentsPerWorkspace();

}