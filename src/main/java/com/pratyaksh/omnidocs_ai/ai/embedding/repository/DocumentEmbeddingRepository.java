package com.pratyaksh.omnidocs_ai.ai.embedding.repository;

import com.pratyaksh.omnidocs_ai.ai.chunk.entity.DocumentChunk;
import com.pratyaksh.omnidocs_ai.ai.embedding.entity.DocumentEmbedding;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DocumentEmbeddingRepository
    extends JpaRepository<DocumentEmbedding, Long> {

  void deleteByChunk_Document_Uuid(UUID documentUuid);

  void deleteByChunk(DocumentChunk chunk);

  void deleteByChunkIn(List<DocumentChunk> chunks);

  @Modifying
  @Query("""
      DELETE FROM DocumentEmbedding e
      WHERE e.chunk.document = :document
      """)
  void deleteByDocument(Document document);
}