package com.pratyaksh.omnidocs_ai.document.service.application;

import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;

public interface DocumentApplicationService {

  UploadDocumentResponse uploadDocument(UploadDocumentRequest request);

}