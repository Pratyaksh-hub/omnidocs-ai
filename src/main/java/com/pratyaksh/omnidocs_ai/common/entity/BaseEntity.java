package com.pratyaksh.omnidocs_ai.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

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
  private boolean deleted = false;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

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