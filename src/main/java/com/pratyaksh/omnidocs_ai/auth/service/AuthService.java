package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.request.LoginRequest;
import com.pratyaksh.omnidocs_ai.auth.request.RefreshTokenRequest;
import com.pratyaksh.omnidocs_ai.auth.request.SignupRequest;
import com.pratyaksh.omnidocs_ai.auth.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

  AuthResponse signup(SignupRequest request,
      HttpServletRequest servletRequest);

  AuthResponse login(
      LoginRequest request,
      HttpServletRequest servletRequest
  );

  AuthResponse refreshToken(RefreshTokenRequest request);

  void logout();

}