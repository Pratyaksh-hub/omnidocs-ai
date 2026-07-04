package com.pratyaksh.omnidocs_ai.document.repository;

import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

  Optional<Document> findByUuidAndDeletedFalse(UUID uuid);

  Page<Document> findByWorkspaceUuidAndDeletedFalse(
      UUID workspaceUuid,
      Pageable pageable
  );

  Page<Document> findByWorkspace_UuidAndDeletedTrue(
      UUID workspaceUuid,
      Pageable pageable
  );

  Page<Document> findByDeletedTrue(Pageable pageable);

  Optional<Document> findByUuidAndWorkspace_OwnerAndDeletedFalse(
      UUID uuid,
      User owner
  );

  Optional<Document> findByUuidAndWorkspace_Owner(
      UUID uuid,
      User owner
  );
}
