package com.pratyaksh.omnidocs_ai.workspace.repository;

import com.pratyaksh.omnidocs_ai.user.entity.User;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository
    extends JpaRepository<Workspace, Long> {

  Optional<Workspace> findByUuidAndDeletedFalse(UUID uuid);

  Optional<Workspace> findByUuidAndOwnerAndDeletedFalse(
      UUID uuid,
      User owner
  );

  Page<Workspace> findAllByOwnerAndDeletedFalse(
      User owner,
      Pageable pageable
  );

}