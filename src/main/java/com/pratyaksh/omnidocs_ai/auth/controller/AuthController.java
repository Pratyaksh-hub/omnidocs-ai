package com.pratyaksh.omnidocs_ai.auth.controller;

import com.pratyaksh.omnidocs_ai.auth.request.LoginRequest;
import com.pratyaksh.omnidocs_ai.auth.request.LogoutRequest;
import com.pratyaksh.omnidocs_ai.auth.request.RefreshTokenRequest;
import com.pratyaksh.omnidocs_ai.auth.request.SignupRequest;
import com.pratyaksh.omnidocs_ai.auth.response.AuthResponse;
import com.pratyaksh.omnidocs_ai.auth.service.AuthService;
import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<AuthResponse>> signup(
      @Valid @RequestBody SignupRequest request) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(authService.signup(request)));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(
      @Valid @RequestBody LoginRequest request,
      HttpServletRequest servletRequest) {

    return ResponseEntity.ok(
        ApiResponse.success(
            authService.login(request, servletRequest)
        )
    );
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
      @Valid @RequestBody RefreshTokenRequest request) {

    return ResponseEntity.ok(
        ApiResponse.success(
            authService.refreshToken(request)
        )
    );
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
      @Valid @RequestBody LogoutRequest request) {

    authService.logout(request);

    return ResponseEntity.ok(
        ApiResponse.success(null)
    );
  }

}