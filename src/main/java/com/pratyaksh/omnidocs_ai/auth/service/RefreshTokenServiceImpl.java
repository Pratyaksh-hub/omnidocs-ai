package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.config.JwtProperties;
import com.pratyaksh.omnidocs_ai.auth.entity.RefreshToken;
import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.auth.jwt.JwtService;
import com.pratyaksh.omnidocs_ai.auth.repository.RefreshTokenRepository;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl
    implements RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtService jwtService;
  private final JwtProperties jwtProperties;

  @Override
  public RefreshToken create(
      UserSession session,
      String token) {

    RefreshToken refreshToken =
        RefreshToken.create(
            session,
            token,
            LocalDateTime.now()
                .plus(jwtProperties.getRefreshTokenExpiration(), ChronoUnit.MILLIS)
        );

    refreshToken.setDeviceName(session.getDeviceName());
    refreshToken.setIpAddress(session.getIpAddress());
    refreshToken.setUserAgent(session.getUserAgent());

    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  @Transactional(readOnly = true)
  public RefreshToken validate(
      String token) {

    if (!jwtService.isTokenValid(token)) {
      throw new IllegalArgumentException("Invalid refresh token.");
    }

    if (!jwtService.isRefreshToken(token)) {
      throw new IllegalArgumentException("Access token cannot be used.");
    }

    RefreshToken refreshToken =
        refreshTokenRepository
            .findByTokenAndRevokedFalseAndDeletedFalse(token)
            .orElseThrow(() ->
                new EntityNotFoundException(
                    "Refresh token not found."
                )
            );

    if (!refreshToken.isActive()) {
      throw new IllegalArgumentException(
          "Refresh token has expired."
      );
    }

    return refreshToken;
  }

  @Override
  public void revoke(
      String token) {

    refreshTokenRepository
        .findByTokenAndRevokedFalseAndDeletedFalse(token)
        .ifPresent(this::revoke);
  }

  @Override
  public void revoke(
      RefreshToken refreshToken) {

    if (!refreshToken.isRevoked()) {

      refreshToken.revoke();

      refreshTokenRepository.save(refreshToken);
    }
  }

  @Override
  public void revokeAll(
      User user) {

    refreshTokenRepository
        .findBySession_UserAndRevokedFalseAndDeletedFalse(user)
        .forEach(this::revoke);
  }

}