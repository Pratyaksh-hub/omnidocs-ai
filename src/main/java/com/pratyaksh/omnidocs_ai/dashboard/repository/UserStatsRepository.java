package com.pratyaksh.omnidocs_ai.dashboard.repository;

import com.pratyaksh.omnidocs_ai.dashboard.entity.UserStatistics;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserStatsRepository
    extends JpaRepository<UserStatistics, Long> {

  Optional<UserStatistics> findByUser(User user);

  @Modifying
  @Transactional
  @Query("""
      update UserStatistics d
      set d.totalWorkspaces = d.totalWorkspaces + 1
      where d.user.id = :userId
      """)
  void incrementWorkspaceCount(
      @Param("userId") Long userId
  );

  @Modifying
  @Transactional
  @Query("""
      update UserStatistics d
      set d.totalWorkspaces =
          case
              when d.totalWorkspaces > 0
              then d.totalWorkspaces - 1
              else 0
          end
      where d.user.id = :userId
      """)
  void decrementWorkspaceCount(
      @Param("userId") Long userId
  );

  @Modifying
  @Transactional
  @Query("""
      update UserStatistics d
      set d.totalDocuments = d.totalDocuments + 1
      where d.user.id = :userId
      """)
  void incrementDocumentCount(
      @Param("userId") Long userId
  );

  @Modifying
  @Transactional
  @Query("""
      update UserStatistics d
      set d.totalDocuments =
          case
              when d.totalDocuments > 0
              then d.totalDocuments - 1
              else 0
          end
      where d.user.id = :userId
      """)
  void decrementDocumentCount(
      @Param("userId") Long userId
  );

  @Modifying
  @Query("""
UPDATE UserStatistics s
SET s.totalDocuments =
(
    SELECT COUNT(d)
    FROM Document d
    WHERE d.workspace.owner.id = :userId
      AND d.deleted = false
)
WHERE s.user.id = :userId
""")
  void refreshDocumentCount(Long userId);

}