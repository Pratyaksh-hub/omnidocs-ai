package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserSessionService {

  UserSession create(
      User user,
      String deviceName,
      String browser,
      String operatingSystem,
      String ipAddress,
      String userAgent
  );

  UserSession findByUuid(UUID uuid);

  List<UserSession> findActiveSessions(User user);

  void touch(UserSession session);

  void revoke(UserSession session);

  void revoke(UUID sessionUuid);

  void revokeAll(User user);

}