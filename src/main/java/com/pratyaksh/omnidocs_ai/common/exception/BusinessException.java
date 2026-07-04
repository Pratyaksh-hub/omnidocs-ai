package com.pratyaksh.omnidocs_ai.common.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  protected BusinessException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }

  protected BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  protected BusinessException(ErrorCode errorCode,
      String message,
      Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }
}