package com.pratyaksh.omnidocs_ai.document.service.upload;

import com.pratyaksh.omnidocs_ai.document.request.RenameDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.request.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.response.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import java.util.UUID;

public interface DocumentUploadService {

  UploadDocumentResponse upload(
      Workspace workspace,
      UploadDocumentRequest request
  );

  DocumentResponse rename(
      UUID documentUuid,
      RenameDocumentRequest request
  );

  DocumentResponse restore(
      UUID documentUuid
  );

  void permanentDelete(
      UUID documentUuid
  );

}