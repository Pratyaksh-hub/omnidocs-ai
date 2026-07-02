package com.pratyaksh.omnidocs_ai.ai.chunk.repository;

import com.pratyaksh.omnidocs_ai.ai.chunk.entity.DocumentChunk;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentChunkRepository
    extends JpaRepository<DocumentChunk, Long> {

  List<DocumentChunk> findByDocument(Document document);

  void deleteByDocument_Uuid(UUID documentUuid);

}