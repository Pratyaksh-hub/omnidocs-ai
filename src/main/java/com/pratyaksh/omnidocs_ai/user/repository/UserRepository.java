package com.pratyaksh.omnidocs_ai.user.repository;

import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUuidAndDeletedFalse(UUID uuid);

  Optional<User> findByEmailIgnoreCaseAndDeletedFalse(String email);

  boolean existsByEmailIgnoreCaseAndDeletedFalse(String email);

}