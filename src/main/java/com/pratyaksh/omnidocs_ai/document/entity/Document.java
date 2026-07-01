package com.pratyaksh.omnidocs_ai.document.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "documents",
    indexes = {
        @Index(name = "idx_document_uuid", columnList = "uuid"),
        @Index(name = "idx_document_workspace", columnList = "workspace_id"),
        @Index(name = "idx_document_status", columnList = "status")
    }
)
public class Document extends BaseEntity {

  @Column(nullable = false, length = 255)
  private String originalFileName;

  @Column(nullable = false, length = 100)
  private String contentType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private DocumentStatus status;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "workspace_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_document_workspace")
  )
  private Workspace workspace;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "stored_file_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_document_stored_file")
  )
  private StoredFile storedFile;

  public static Document create(
      Workspace workspace,
      StoredFile storedFile,
      String originalFileName,
      String contentType) {

    Document document = new Document();
    document.setWorkspace(workspace);
    document.setStoredFile(storedFile);
    document.setOriginalFileName(originalFileName);
    document.setContentType(contentType);

    return document;
  }

  @PrePersist
  protected void prePersist() {
    if (status == null) {
      status = DocumentStatus.UPLOADED;
    }
  }
}