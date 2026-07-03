package com.pratyaksh.omnidocs_ai.auth.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

  private UUID userUuid;

  private String firstName;

  private String lastName;

  private String email;

  private String role;

  private String accessToken;

  private String refreshToken;

  private LocalDateTime accessTokenExpiresAt;

  private LocalDateTime refreshTokenExpiresAt;

  private Long accessTokenExpiresIn;

  private Long refreshTokenExpiresIn;
}