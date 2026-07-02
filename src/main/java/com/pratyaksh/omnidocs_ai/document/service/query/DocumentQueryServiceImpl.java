package com.pratyaksh.omnidocs_ai.document.service.query;

import com.pratyaksh.omnidocs_ai.document.response.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentSummaryResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentNotFoundException;
import com.pratyaksh.omnidocs_ai.document.mapper.DocumentMapper;
import com.pratyaksh.omnidocs_ai.document.repository.DocumentRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentQueryServiceImpl implements DocumentQueryService {

  private final DocumentRepository documentRepository;
  private final DocumentMapper documentMapper;

  @Override
  public DocumentResponse getDocument(UUID documentUuid) {

    Document document = documentRepository.findByUuidAndDeletedFalse(documentUuid)
        .orElseThrow(() -> new DocumentNotFoundException(documentUuid));

    return documentMapper.toDocumentResponse(document);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<DocumentSummaryResponse> getDocuments(
      UUID workspaceUuid,
      Pageable pageable) {

    return documentRepository
        .findByWorkspaceUuidAndDeletedFalse(workspaceUuid, pageable)
        .map(documentMapper::toSummaryResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Document getEntity(UUID documentUuid) {

    return documentRepository
        .findByUuidAndDeletedFalse(documentUuid)
        .orElseThrow(() ->
            new DocumentNotFoundException(documentUuid));
  }
}