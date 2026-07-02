package com.pratyaksh.omnidocs_ai.document.facade;

import com.pratyaksh.omnidocs_ai.common.response.PageResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentSummaryResponse;
import com.pratyaksh.omnidocs_ai.document.response.DownloadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.request.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.response.UploadDocumentResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface DocumentFacade {

  UploadDocumentResponse uploadDocument(UploadDocumentRequest request);

  DocumentResponse getDocument(UUID documentUuid);

  DownloadDocumentResponse downloadDocument(UUID documentUuid);

  PageResponse<DocumentSummaryResponse> getDocuments(
      UUID workspaceUuid,
      Pageable pageable
  );

  void deleteDocument(UUID documentUuid);

  PageResponse<DocumentSummaryResponse> getDeletedDocuments(
      UUID workspaceUuid,
      Pageable pageable
  );

  PageResponse<DocumentSummaryResponse> getDeletedDocuments(
      Pageable pageable
  );
}