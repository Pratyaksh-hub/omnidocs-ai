package com.pratyaksh.omnidocs_ai.ai.embedding.repository;

import com.pratyaksh.omnidocs_ai.ai.embedding.entity.DocumentEmbedding;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentEmbeddingRepository
    extends JpaRepository<DocumentEmbedding, Long> {

  void deleteByChunk_Document_Uuid(UUID documentUuid);

}