package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.config.JwtProperties;
import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.auth.repository.UserSessionRepository;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSessionServiceImpl
    implements UserSessionService {

  private final UserSessionRepository repository;
  private final JwtProperties jwtProperties;

  @Override
  public UserSession create(
      User user,
      String deviceName,
      String browser,
      String operatingSystem,
      String ipAddress,
      String userAgent) {

    UserSession session =
        UserSession.create(
            user,
            deviceName,
            browser,
            operatingSystem,
            ipAddress,
            userAgent,
            LocalDateTime.now()
                .plus(jwtProperties.getRefreshTokenExpiration(), ChronoUnit.MILLIS)
        );

    return repository.save(session);
  }

  @Override
  @Transactional(readOnly = true)
  public UserSession findByUuid(UUID uuid) {

    return repository
        .findByUuidAndDeletedFalse(uuid)
        .orElseThrow(() ->
            new EntityNotFoundException(
                "Session not found."
            )
        );
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserSession> findActiveSessions(User user) {

    return repository.findByUserAndRevokedFalseAndDeletedFalse(user);
  }

  @Override
  public void touch(UserSession session) {

    session.touch();

    repository.save(session);
  }

  @Override
  public void revoke(UserSession session) {

    if (!session.isRevoked()) {

      session.revoke();

      repository.save(session);
    }
  }

  @Override
  public void revoke(UUID sessionUuid) {

    UserSession session = findByUuid(sessionUuid);

    revoke(session);
  }

  @Override
  public void revokeAll(User user) {

    repository
        .findByUserAndRevokedFalseAndDeletedFalse(user)
        .forEach(this::revoke);
  }

}