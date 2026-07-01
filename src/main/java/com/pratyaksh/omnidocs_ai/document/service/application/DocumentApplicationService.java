package com.pratyaksh.omnidocs_ai.document.service.application;

import com.pratyaksh.omnidocs_ai.document.dto.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.dto.DownloadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;
import java.util.UUID;

public interface DocumentApplicationService {

  UploadDocumentResponse uploadDocument(UploadDocumentRequest request);

  DocumentResponse getDocument(UUID documentUuid);

  DownloadDocumentResponse downloadDocument(UUID documentUuid);
}