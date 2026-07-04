package com.pratyaksh.omnidocs_ai.common.response;

import com.pratyaksh.omnidocs_ai.common.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

  private boolean success;

  private T data;

  private ErrorResponse error;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .success(true)
        .data(data)
        .build();
  }

  public static <T> ApiResponse<T> failure(ErrorResponse error) {
    return ApiResponse.<T>builder()
        .success(false)
        .error(error)
        .build();
  }
}