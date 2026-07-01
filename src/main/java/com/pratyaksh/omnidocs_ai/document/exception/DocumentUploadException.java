package com.pratyaksh.omnidocs_ai.document.exception;

import com.pratyaksh.omnidocs_ai.common.exception.BusinessException;
import com.pratyaksh.omnidocs_ai.common.exception.ErrorCode;

public class DocumentUploadException extends BusinessException {

  public DocumentUploadException(String message, Throwable cause) {
    super(ErrorCode.STORAGE_ERROR, message, cause);
  }

}