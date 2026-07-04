package com.pratyaksh.omnidocs_ai.subscription.policy;

import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class PremiumUploadPolicy implements UploadPolicy {

  @Override
  public Set<String> allowedContentTypes() {

    return Set.of(

        MediaType.APPLICATION_PDF_VALUE,

        MediaType.TEXT_PLAIN_VALUE,

        MediaType.IMAGE_JPEG_VALUE,

        MediaType.IMAGE_PNG_VALUE,

        "image/webp",

        "image/heif",

        "image/heic",

        "application/msword",

        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",

        "application/vnd.ms-excel",

        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",

        "application/vnd.ms-powerpoint",

        "application/vnd.openxmlformats-officedocument.presentationml.presentation"
    );
  }

  @Override
  public long maxFileSize() {
    return 500L * 1024 * 1024;
  }

  @Override
  public long maxWorkspaceCount() {
    return Long.MAX_VALUE;
  }

  @Override
  public long maxDocumentsPerWorkspace() {
    return Long.MAX_VALUE;
  }

}