package com.pratyaksh.omnidocs_ai.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratyaksh.omnidocs_ai.common.exception.ErrorResponse;
import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint
    implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException
  ) throws IOException, ServletException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    ApiResponse<Void> apiResponse =
        ApiResponse.failure(
            ErrorResponse.builder()
                .code("UNAUTHORIZED")
                .message("Authentication is required.")
                .build()
        );

    objectMapper.writeValue(
        response.getOutputStream(),
        apiResponse
    );
  }

}