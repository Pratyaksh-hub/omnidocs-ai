package com.pratyaksh.omnidocs_ai.document.service.application;

import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Workspace;
import com.pratyaksh.omnidocs_ai.document.service.DocumentUploadService;
import com.pratyaksh.omnidocs_ai.workspace.service.WorkspaceService;
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

  @Override
  public UploadDocumentResponse uploadDocument(
      UploadDocumentRequest request) {

    Workspace workspace =
        workspaceService.getEntity(request.getWorkspaceUuid());

    return documentUploadService.upload(workspace, request);
  }
}