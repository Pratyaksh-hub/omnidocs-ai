package com.pratyaksh.omnidocs_ai.subscription.policy;

import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class PlusUploadPolicy implements UploadPolicy {

  @Override
  public Set<String> allowedContentTypes() {

    return Set.of(

        MediaType.APPLICATION_PDF_VALUE,

        MediaType.TEXT_PLAIN_VALUE,

        MediaType.IMAGE_JPEG_VALUE,

        MediaType.IMAGE_PNG_VALUE,

        "application/msword",

        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );
  }

  @Override
  public long maxFileSize() {
    return 50L * 1024 * 1024;
  }

  @Override
  public long maxWorkspaceCount() {
    return 100;
  }

  @Override
  public long maxDocumentsPerWorkspace() {
    return 5000;
  }

}