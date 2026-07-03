package com.pratyaksh.omnidocs_ai.auth.jwt;

import com.pratyaksh.omnidocs_ai.auth.config.JwtProperties;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final JwtProperties jwtProperties;

  private SecretKey signingKey;

  @PostConstruct
  public void init() {
    signingKey = Keys.hmacShaKeyFor(
        jwtProperties.getSecret()
            .getBytes(StandardCharsets.UTF_8)
    );
  }

  @Override
  public String generateAccessToken(User user) {

    return buildToken(
        user,
        ACCESS_TOKEN,
        jwtProperties.getAccessTokenExpiration()
    );
  }

  @Override
  public String generateRefreshToken(User user) {

    return buildToken(
        user,
        REFRESH_TOKEN,
        jwtProperties.getRefreshTokenExpiration()
    );
  }

  private String buildToken(
      User user,
      String tokenType,
      long expiration) {

    long now = System.currentTimeMillis();

    Map<String, Object> claims = new HashMap<>();

    claims.put("uid", user.getUuid().toString());
    claims.put("role", user.getRole().name());
    claims.put("type", tokenType);

    return Jwts.builder()
        .claims(claims)
        .subject(user.getEmail())
        .issuedAt(new Date(now))
        .expiration(new Date(now + expiration))
        .signWith(signingKey)
        .compact();
  }

  @Override
  public String extractEmail(String token) {
    return extractClaims(token).getSubject();
  }

  @Override
  public UUID extractUserUuid(String token) {

    return UUID.fromString(
        extractClaims(token)
            .get("uid", String.class)
    );
  }

  @Override
  public String extractTokenType(String token) {

    return extractClaims(token)
        .get("type", String.class);
  }

  @Override
  public boolean isAccessToken(String token) {
    return ACCESS_TOKEN.equals(extractTokenType(token));
  }

  @Override
  public boolean isRefreshToken(String token) {
    return REFRESH_TOKEN.equals(extractTokenType(token));
  }

  @Override
  public boolean isTokenValid(String token) {

    try {

      Claims claims = extractClaims(token);

      return claims.getExpiration()
          .after(new Date());

    } catch (Exception ex) {

      return false;
    }
  }

  private Claims extractClaims(String token) {

    return Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

}