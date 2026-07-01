package com.pratyaksh.omnidocs_ai.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),

  VALIDATION_ERROR(HttpStatus.BAD_REQUEST),

  WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND),

  DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND),

  DUPLICATE_DOCUMENT(HttpStatus.CONFLICT),

  INVALID_DOCUMENT(HttpStatus.BAD_REQUEST),

  STORAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

  private final HttpStatus httpStatus;

}