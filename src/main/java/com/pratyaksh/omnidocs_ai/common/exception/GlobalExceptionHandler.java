package com.pratyaksh.omnidocs_ai.common.exception;

import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Void>> handleBusinessException(
      BusinessException ex,
      HttpServletRequest request) {

    ErrorResponse error = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getErrorCode().getHttpStatus().value())
        .error(ex.getErrorCode().getHttpStatus().getReasonPhrase())
        .code(ex.getErrorCode().name())
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity
        .status(ex.getErrorCode().getHttpStatus())
        .body(ApiResponse.<Void>builder()
            .success(false)
            .error(error)
            .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationException(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    String message = ex.getBindingResult()
        .getFieldError()
        .getDefaultMessage();

    ErrorResponse error = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(400)
        .error("Bad Request")
        .code(ErrorCode.VALIDATION_ERROR.name())
        .message(message)
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.badRequest()
        .body(ApiResponse.<Void>builder()
            .success(false)
            .error(error)
            .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(
      Exception ex,
      HttpServletRequest request) {

    log.error("Unhandled exception", ex);

    ErrorResponse error = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(500)
        .error("Internal Server Error")
        .code(ErrorCode.INTERNAL_SERVER_ERROR.name())
        .message("Something went wrong.")
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.internalServerError()
        .body(ApiResponse.<Void>builder()
            .success(false)
            .error(error)
            .build());
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(
      NoResourceFoundException ex) {

    ErrorResponse error = ErrorResponse.builder()
        .code("NOT_FOUND")
        .message("Resource not found.")
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.failure(error));
  }
}