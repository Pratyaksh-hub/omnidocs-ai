package com.pratyaksh.omnidocs_ai.auth.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "refresh_tokens",
    indexes = {
        @Index(name = "idx_refresh_token_uuid", columnList = "uuid"),
        @Index(name = "idx_refresh_token_session", columnList = "session_id"),
        @Index(name = "idx_refresh_token_token", columnList = "token")
    }
)
public class RefreshToken extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "session_id", nullable = false)
  private UserSession session;

  @Column(nullable = false, unique = true, length = 512)
  private String token;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  @Column(nullable = false)
  private boolean revoked;

  private LocalDateTime revokedAt;

  @Column(length = 255)
  private String deviceName;

  @Column(length = 100)
  private String ipAddress;

  @Column(length = 1000)
  private String userAgent;

  @PrePersist
  private void prePersist() {

    if (uuid == null) {
      uuid = UUID.randomUUID();
    }
  }

  public static RefreshToken create(
      UserSession session,
      String token,
      LocalDateTime expiresAt) {

    RefreshToken refreshToken = new RefreshToken();

    refreshToken.uuid = UUID.randomUUID();
    refreshToken.session = session;
    refreshToken.token = token;
    refreshToken.expiresAt = expiresAt;
    refreshToken.revoked = false;

    refreshToken.deviceName = session.getDeviceName();
    refreshToken.ipAddress = session.getIpAddress();
    refreshToken.userAgent = session.getUserAgent();

    return refreshToken;
  }

  public void revoke() {

    revoked = true;
    revokedAt = LocalDateTime.now();
  }

  public boolean isExpired() {
    return expiresAt.isBefore(LocalDateTime.now());
  }

  public boolean isActive() {
    return !revoked && !isExpired();
  }

  public User getUser() {
    return session.getUser();
  }

}