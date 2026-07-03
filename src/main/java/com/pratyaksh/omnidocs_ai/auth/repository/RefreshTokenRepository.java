package com.pratyaksh.omnidocs_ai.auth.repository;

import com.pratyaksh.omnidocs_ai.auth.entity.RefreshToken;
import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository
    extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByTokenAndRevokedFalseAndDeletedFalse(
      String token
  );

  Optional<RefreshToken> findByUuidAndDeletedFalse(
      UUID uuid
  );

  List<RefreshToken> findBySession(
      UserSession session
  );

  List<RefreshToken> findBySessionAndRevokedFalseAndDeletedFalse(
      UserSession session
  );

  void deleteBySession(
      UserSession session
  );

  void deleteBySession_User(
      User user
  );

  List<RefreshToken> findBySession_UserAndRevokedFalseAndDeletedFalse(User user);

}