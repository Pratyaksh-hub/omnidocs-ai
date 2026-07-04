package com.pratyaksh.omnidocs_ai.auth.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_sessions")
public class UserSession extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private UUID uuid;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(length = 255)
  private String deviceName;

  @Column(length = 255)
  private String browser;

  @Column(length = 255)
  private String operatingSystem;

  @Column(length = 100)
  private String ipAddress;

  @Column(columnDefinition = "TEXT")
  private String userAgent;

  @Column(nullable = false)
  private LocalDateTime lastActivityAt;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  @Column(nullable = false)
  private boolean revoked;

  public static UserSession create(
      User user,
      String deviceName,
      String browser,
      String operatingSystem,
      String ipAddress,
      String userAgent,
      LocalDateTime expiresAt) {

    UserSession session = new UserSession();

    session.uuid = UUID.randomUUID();
    session.user = user;
    session.deviceName = deviceName;
    session.browser = browser;
    session.operatingSystem = operatingSystem;
    session.ipAddress = ipAddress;
    session.userAgent = userAgent;
    session.lastActivityAt = LocalDateTime.now();
    session.expiresAt = expiresAt;
    session.revoked = false;

    return session;
  }

  public void touch() {
    this.lastActivityAt = LocalDateTime.now();
  }

  public void revoke() {
    this.revoked = true;
  }

  public boolean isExpired() {
    return expiresAt.isBefore(LocalDateTime.now());
  }

  public boolean isActive() {
    return !revoked && !isExpired();
  }

  public User getOwner() {
    return user;
  }

}