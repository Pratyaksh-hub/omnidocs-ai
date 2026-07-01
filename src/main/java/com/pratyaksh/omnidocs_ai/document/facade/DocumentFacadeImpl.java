package com.pratyaksh.omnidocs_ai.document.facade;

import com.pratyaksh.omnidocs_ai.common.response.PageResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentSummaryResponse;
import com.pratyaksh.omnidocs_ai.document.response.DownloadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.request.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.response.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.document.service.download.DocumentDownloadService;
import com.pratyaksh.omnidocs_ai.document.service.query.DocumentQueryService;
import com.pratyaksh.omnidocs_ai.document.service.upload.DocumentUploadService;
import com.pratyaksh.omnidocs_ai.workspace.service.query.WorkspaceQueryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentFacadeImpl
    implements DocumentFacade {

  private final WorkspaceQueryService workspaceService;
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

  @Override
  @Transactional(readOnly = true)
  public PageResponse<DocumentSummaryResponse> getDocuments(
      UUID workspaceUuid,
      Pageable pageable) {

    Page<DocumentSummaryResponse> page =
        documentQueryService.getDocuments(workspaceUuid, pageable);

    return PageResponse.<DocumentSummaryResponse>builder()
        .content(page.getContent())
        .page(page.getNumber())
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .first(page.isFirst())
        .last(page.isLast())
        .build();
  }
}