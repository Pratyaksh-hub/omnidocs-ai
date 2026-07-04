package com.pratyaksh.omnidocs_ai.ai.chunk.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
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
@Table(name = "document_chunks")
public class DocumentChunk extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "document_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_chunk_document")
  )
  private Document document;

  @Column(nullable = false)
  private Integer chunkIndex;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(nullable = false)
  private Integer characterCount;

  @Column
  private Integer tokenCount;

  public static DocumentChunk create(
      Document document,
      Integer chunkIndex,
      String content,
      Integer characterCount
  ) {

    DocumentChunk chunk = new DocumentChunk();

    chunk.document = document;
    chunk.chunkIndex = chunkIndex;
    chunk.content = content;
    chunk.characterCount = characterCount;

    return chunk;
  }

  public void updateTokenCount(Integer tokenCount) {
    this.tokenCount = tokenCount;
  }
}