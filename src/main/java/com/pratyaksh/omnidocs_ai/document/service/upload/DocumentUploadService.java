package com.pratyaksh.omnidocs_ai.document.service.upload;

import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Workspace;

public interface DocumentUploadService {

  UploadDocumentResponse upload(
      Workspace workspace,
      UploadDocumentRequest request
  );

}