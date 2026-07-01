package com.pratyaksh.omnidocs_ai.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.ErrorResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

  private boolean success;

  private T data;

  private ErrorResponse error;
}