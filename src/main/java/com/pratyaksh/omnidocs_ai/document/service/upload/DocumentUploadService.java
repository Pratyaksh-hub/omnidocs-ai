package com.pratyaksh.omnidocs_ai.document.service.upload;

import com.pratyaksh.omnidocs_ai.document.request.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.response.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;

public interface DocumentUploadService {

  UploadDocumentResponse upload(
      Workspace workspace,
      UploadDocumentRequest request
  );

}