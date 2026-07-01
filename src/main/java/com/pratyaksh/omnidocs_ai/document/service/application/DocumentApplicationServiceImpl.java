package com.pratyaksh.omnidocs_ai.document.service.application;

import com.pratyaksh.omnidocs_ai.document.dto.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.dto.DownloadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Workspace;
import com.pratyaksh.omnidocs_ai.document.service.download.DocumentDownloadService;
import com.pratyaksh.omnidocs_ai.document.service.query.DocumentQueryService;
import com.pratyaksh.omnidocs_ai.document.service.upload.DocumentUploadService;
import com.pratyaksh.omnidocs_ai.workspace.service.WorkspaceService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentApplicationServiceImpl
    implements DocumentApplicationService {

  private final WorkspaceService workspaceService;
  private final DocumentUploadService documentUploadService;
  private final DocumentQueryService documentQueryService;
  private final DocumentDownloadService documentDownloadService;

  @Override
  public UploadDocumentResponse uploadDocument(
      UploadDocumentRequest request) {

    Workspace workspace =
        workspaceService.getEntity(request.getWorkspaceUuid());

    return documentUploadService.upload(workspace, request);
  }

  @Override
  @Transactional(readOnly = true)
  public DocumentResponse getDocument(UUID documentUuid) {
    return documentQueryService.getDocument(documentUuid);
  }

  @Override
  @Transactional(readOnly = true)
  public DownloadDocumentResponse downloadDocument(UUID documentUuid) {
    return documentDownloadService.download(documentUuid);
  }
}