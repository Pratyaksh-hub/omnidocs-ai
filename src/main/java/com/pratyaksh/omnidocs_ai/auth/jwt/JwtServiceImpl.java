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

  private static final String ACCESS = "ACCESS";
  private static final String REFRESH = "REFRESH";

  @PostConstruct
  public void init() {
    signingKey = Keys.hmacShaKeyFor(
        jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
    );
  }

  @Override
  public String generateAccessToken(User user) {
    return buildToken(user, ACCESS, jwtProperties.getAccessTokenExpiration());
  }

  @Override
  public String generateRefreshToken(User user) {
    return buildToken(user, REFRESH, jwtProperties.getRefreshTokenExpiration());
  }

  private String buildToken(User user, String type, long exp) {

    long now = System.currentTimeMillis();

    Map<String, Object> claims = new HashMap<>();
    claims.put("uid", user.getUuid().toString());
    claims.put("role", user.getRole().name());
    claims.put("type", type);

    return Jwts.builder()
        .claims(claims)
        .subject(user.getEmail())
        .issuedAt(new Date(now))
        .expiration(new Date(now + exp))
        .signWith(signingKey)
        .compact();
  }

  @Override
  public String extractEmail(String token) {
    return extract(token).getSubject();
  }

  @Override
  public UUID extractUserUuid(String token) {
    return UUID.fromString(extract(token).get("uid", String.class));
  }

  @Override
  public String extractTokenType(String token) {
    return extract(token).get("type", String.class);
  }

  @Override
  public boolean isAccessToken(String token) {
    return ACCESS.equals(extractTokenType(token));
  }

  @Override
  public boolean isRefreshToken(String token) {
    return REFRESH.equals(extractTokenType(token));
  }

  @Override
  public boolean isTokenValid(String token) {
    try {
      return extract(token).getExpiration().after(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  private Claims extract(String token) {
    return Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  @Override
  public long getAccessTokenExpiration() {
    return jwtProperties.getAccessTokenExpiration();
  }

  @Override
  public long getRefreshTokenExpiration() {
    return jwtProperties.getRefreshTokenExpiration();
  }
}