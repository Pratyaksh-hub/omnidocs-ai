package com.pratyaksh.omnidocs_ai.workspace.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "workspaces",
    indexes = {
        @Index(name = "idx_workspace_uuid", columnList = "uuid")
    }
)
public class Workspace extends BaseEntity {

  @Column(nullable = false, length = 150)
  private String name;

  @Column(length = 1000)
  private String description;

  public static Workspace create(
      String name,
      String description
  ) {

    Workspace workspace = new Workspace();
    workspace.name = name;
    workspace.description = description;

    return workspace;
  }

  public void rename(String name) {
    this.name = name;
  }

  public void update(String name, String description) {

    if (name != null) {
      this.name = name;
    }

    if (description != null) {
      this.description = description;
    }
  }
}