package com.pratyaksh.omnidocs_ai.auth.repository;

import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository
    extends JpaRepository<UserSession, Long> {

  Optional<UserSession> findByUuidAndDeletedFalse(
      UUID uuid
  );

  Optional<UserSession> findByUuidAndRevokedFalseAndDeletedFalse(
      UUID uuid
  );

  List<UserSession> findByUserAndDeletedFalse(
      User user
  );

  List<UserSession> findByUserAndRevokedFalseAndDeletedFalse(
      User user
  );

}