package com.pratyaksh.omnidocs_ai.dashboard.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "user_statistics",
    indexes = {
        @Index(name = "idx_user_statistics_uuid", columnList = "uuid"),
        @Index(name = "idx_user_statistics_user", columnList = "user_id")
    }
)
public class UserStatistics extends BaseEntity {

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  @Column(nullable = false)
  private long totalWorkspaces;

  @Column(nullable = false)
  private long totalDocuments;

  @PrePersist
  private void prePersist() {

    if (uuid == null) {
      uuid = UUID.randomUUID();
    }
  }

  public static UserStatistics create(User user) {

    UserStatistics statistics = new UserStatistics();

    statistics.user = user;
    statistics.totalWorkspaces = 0;
    statistics.totalDocuments = 0;

    return statistics;
  }

  public void incrementWorkspaces() {
    totalWorkspaces++;
  }

  public void decrementWorkspaces() {

    if (totalWorkspaces > 0) {
      totalWorkspaces--;
    }
  }

  public void incrementDocuments() {
    totalDocuments++;
  }

  public void decrementDocuments() {

    if (totalDocuments > 0) {
      totalDocuments--;
    }
  }

}