package com.pratyaksh.omnidocs_ai.document.service.query;

import com.pratyaksh.omnidocs_ai.document.response.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentSummaryResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentQueryService {

  DocumentResponse getDocument(UUID documentUuid);

  Page<DocumentSummaryResponse> getDocuments(
      UUID workspaceUuid,
      Pageable pageable
  );
}