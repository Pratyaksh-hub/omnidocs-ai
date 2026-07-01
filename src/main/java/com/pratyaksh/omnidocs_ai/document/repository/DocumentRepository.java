package com.pratyaksh.omnidocs_ai.document.repository;

import com.pratyaksh.omnidocs_ai.document.entity.Document;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

  Optional<Document> findByUuid(UUID uuid);

  Page<Document> findAllByWorkspaceUuid(
      UUID workspaceUuid,
      Pageable pageable
  );

}
