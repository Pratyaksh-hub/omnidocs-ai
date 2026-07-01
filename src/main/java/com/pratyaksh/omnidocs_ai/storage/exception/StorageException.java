package com.pratyaksh.omnidocs_ai.storage.exception;

import com.pratyaksh.omnidocs_ai.common.exception.BusinessException;
import com.pratyaksh.omnidocs_ai.common.exception.ErrorCode;

public class StorageException extends BusinessException {

  public StorageException(String message) {
    super(ErrorCode.STORAGE_ERROR, message);
  }

  public StorageException(String message, Throwable cause) {
    super(ErrorCode.STORAGE_ERROR, message);
    initCause(cause);
  }

}