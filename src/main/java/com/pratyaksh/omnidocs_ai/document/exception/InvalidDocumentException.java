package com.pratyaksh.omnidocs_ai.document.exception;

import com.pratyaksh.omnidocs_ai.common.exception.BusinessException;
import com.pratyaksh.omnidocs_ai.common.exception.ErrorCode;

public class InvalidDocumentException extends BusinessException {

  public InvalidDocumentException(String message) {
    super(ErrorCode.INVALID_DOCUMENT, message);
  }

}