package com.pratyaksh.omnidocs_ai.document.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "workspaces",
    indexes = {
        @Index(name = "idx_workspace_uuid", columnList = "uuid")
    }
)
public class Workspace extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(length = 1000)
  private String description;

  @PrePersist
  private void prePersist() {
    if (uuid == null) {
      uuid = UUID.randomUUID();
    }
  }

}