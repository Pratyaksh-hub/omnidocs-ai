package com.pratyaksh.omnidocs_ai.auth.jwt;

import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.UUID;

public interface JwtService {

  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  String extractEmail(String token);

  UUID extractUserUuid(String token);

  String extractTokenType(String token);

  boolean isAccessToken(String token);

  boolean isRefreshToken(String token);

  boolean isTokenValid(String token);

  long getAccessTokenExpiration();

  long getRefreshTokenExpiration();
}