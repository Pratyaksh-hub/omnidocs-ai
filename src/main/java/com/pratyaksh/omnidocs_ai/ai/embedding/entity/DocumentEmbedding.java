package com.pratyaksh.omnidocs_ai.ai.embedding.entity;

import com.pratyaksh.omnidocs_ai.ai.chunk.entity.DocumentChunk;
import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document_embeddings")
public class DocumentEmbedding extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "chunk_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_embedding_chunk")
  )
  private DocumentChunk chunk;

  @Column(nullable = false, length = 50)
  private String provider;

  @Column(nullable = false, length = 100)
  private String model;

  @Column(nullable = false)
  private Integer dimensions;

  @Column(
      nullable = false,
      columnDefinition = "vector(1536)"
  )
  private float[] embedding;

  public static DocumentEmbedding create(
      DocumentChunk chunk,
      String provider,
      String model,
      float[] embedding
  ) {

    DocumentEmbedding entity = new DocumentEmbedding();

    entity.chunk = chunk;
    entity.provider = provider;
    entity.model = model;
    entity.dimensions = embedding.length;
    entity.embedding = embedding;

    return entity;
  }
}