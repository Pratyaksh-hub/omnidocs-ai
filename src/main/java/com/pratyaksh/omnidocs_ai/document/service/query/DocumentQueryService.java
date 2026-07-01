package com.pratyaksh.omnidocs_ai.document.service.query;

import com.pratyaksh.omnidocs_ai.document.dto.DocumentResponse;
import java.util.UUID;

public interface DocumentQueryService {

  DocumentResponse getDocument(UUID documentUuid);

}