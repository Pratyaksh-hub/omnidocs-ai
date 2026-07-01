package com.pratyaksh.omnidocs_ai.workspace.repository;

import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

  Optional<Workspace> findByUuid(UUID uuid);

  boolean existsByUuid(UUID uuid);

}
