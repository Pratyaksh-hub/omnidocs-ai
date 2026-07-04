package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.entity.RefreshToken;
import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.user.entity.User;

public interface RefreshTokenService {

  RefreshToken create(
      UserSession session,
      String token
  );

  RefreshToken validate(String token);

  void revoke(String token);

  void revoke(RefreshToken refreshToken);

  void revokeAll(User user);

}