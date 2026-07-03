package com.pratyaksh.omnidocs_ai.workspace.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "workspaces",
    indexes = {
        @Index(name = "idx_workspace_uuid", columnList = "uuid"),
        @Index(name = "idx_workspace_owner", columnList = "owner_id")
    }
)
public class Workspace extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(length = 1000)
  private String description;

  public static Workspace create(
      User owner,
      String name,
      String description
  ) {

    Workspace workspace = new Workspace();
    workspace.owner = owner;
    workspace.name = name;
    workspace.description = description;

    return workspace;
  }

  public void rename(String name) {
    this.name = name;
  }

  public void update(
      String name,
      String description
  ) {

    if (name != null) {
      this.name = name;
    }

    if (description != null) {
      this.description = description;
    }
  }

}