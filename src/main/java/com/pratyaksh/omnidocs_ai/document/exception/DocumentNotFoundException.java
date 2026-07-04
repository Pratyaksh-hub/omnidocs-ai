package com.pratyaksh.omnidocs_ai.document.exception;

import com.pratyaksh.omnidocs_ai.common.exception.BusinessException;
import com.pratyaksh.omnidocs_ai.common.exception.ErrorCode;
import java.util.UUID;

public class DocumentNotFoundException extends BusinessException {

  public DocumentNotFoundException(UUID uuid) {
    super(
        ErrorCode.DOCUMENT_NOT_FOUND,
        "Document not found : " + uuid
    );
  }

}