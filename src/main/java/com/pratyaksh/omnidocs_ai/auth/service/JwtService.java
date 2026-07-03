package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtProperties jwtProperties;

  private SecretKey signingKey;

  @PostConstruct
  public void init() {

    this.signingKey =
        Keys.hmacShaKeyFor(
            jwtProperties.getSecret()
                .getBytes(StandardCharsets.UTF_8)
        );
  }

  public String generateAccessToken(
      UUID userUuid,
      String email,
      String role) {

    return buildToken(
        userUuid,
        email,
        role,
        jwtProperties.getAccessTokenExpiration()
    );
  }

  public String generateRefreshToken(
      UUID userUuid,
      String email,
      String role) {

    return buildToken(
        userUuid,
        email,
        role,
        jwtProperties.getRefreshTokenExpiration()
    );
  }

  private String buildToken(
      UUID userUuid,
      String email,
      String role,
      long expiration) {

    long nowMillis = System.currentTimeMillis();

    Date issuedAt = new Date(nowMillis);

    Date expiresAt =
        new Date(nowMillis + expiration);

    return Jwts.builder()
        .subject(email)
        .claims(
            Map.of(
                "uid", userUuid.toString(),
                "role", role
            )
        )
        .issuedAt(issuedAt)
        .expiration(expiresAt)
        .signWith(signingKey)
        .compact();
  }

  public String extractEmail(
      String token) {

    return extractClaims(token)
        .getSubject();
  }

  public UUID extractUserUuid(
      String token) {

    return UUID.fromString(
        extractClaims(token)
            .get("uid", String.class)
    );
  }

  public String extractRole(
      String token) {

    return extractClaims(token)
        .get("role", String.class);
  }

  public Date extractExpiration(
      String token) {

    return extractClaims(token)
        .getExpiration();
  }

  public boolean isTokenExpired(
      String token) {

    return extractExpiration(token)
        .before(new Date());
  }

  public boolean isTokenValid(
      String token) {

    try {
      return !isTokenExpired(token);
    } catch (Exception ex) {
      return false;
    }
  }

  private Claims extractClaims(
      String token) {

    return Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

}