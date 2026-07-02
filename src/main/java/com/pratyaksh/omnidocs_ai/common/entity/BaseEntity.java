package com.pratyaksh.omnidocs_ai.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  protected UUID uuid;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Version
  @Column(nullable = false)
  private Long version;

  @Column(nullable = false)
  protected boolean deleted = false;

  @Column(name = "deleted_at")
  protected LocalDateTime deletedAt;

  public void markDeleted() {
    this.deleted = true;
    this.deletedAt = LocalDateTime.now();
  }

  public void restore() {
    this.deleted = false;
    this.deletedAt = null;
  }

  @PrePersist
  protected void prePersistBase() {
    if (uuid == null) {
      uuid = UUID.randomUUID();
    }
  }
}