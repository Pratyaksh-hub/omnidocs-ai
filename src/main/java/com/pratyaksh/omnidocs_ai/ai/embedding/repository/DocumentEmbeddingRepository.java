package com.pratyaksh.omnidocs_ai.ai.embedding.repository;

import com.pratyaksh.omnidocs_ai.ai.embedding.entity.DocumentEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentEmbeddingRepository
    extends JpaRepository<DocumentEmbedding, Long> {

}