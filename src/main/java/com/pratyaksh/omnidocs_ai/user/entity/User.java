package com.pratyaksh.omnidocs_ai.user.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "users",
    indexes = {
        @Index(name = "idx_users_uuid", columnList = "uuid"),
        @Index(name = "idx_users_email", columnList = "email")
    }
)
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  @Column(nullable = false, length = 100)
  private String firstName;

  @Column(length = 100)
  private String lastName;

  @Column(nullable = false, unique = true, length = 255)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private UserRole role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 40)
  private UserStatus status;

  @Column(nullable = false)
  private boolean emailVerified;

  private LocalDateTime emailVerifiedAt;

  @Column(nullable = false)
  private Integer failedLoginAttempts;

  private LocalDateTime lockedUntil;

  private LocalDateTime lastLoginAt;

  private LocalDateTime lastPasswordChangedAt;

  @PrePersist
  private void prePersist() {

    if (uuid == null) {
      uuid = UUID.randomUUID();
    }

    if (role == null) {
      role = UserRole.USER;
    }

    if (status == null) {
      status = UserStatus.PENDING_VERIFICATION;
    }

    if (failedLoginAttempts == null) {
      failedLoginAttempts = 0;
    }
  }

  public static User create(
      String firstName,
      String lastName,
      String email,
      String passwordHash) {

    User user = new User();

    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email.toLowerCase().trim());
    user.setPasswordHash(passwordHash);

    user.setRole(UserRole.USER);
    user.setStatus(UserStatus.PENDING_VERIFICATION);

    user.setEmailVerified(false);

    user.setFailedLoginAttempts(0);

    user.setLastPasswordChangedAt(LocalDateTime.now());

    return user;
  }

  public void activate() {

    this.status = UserStatus.ACTIVE;
    this.emailVerified = true;
    this.emailVerifiedAt = LocalDateTime.now();
  }

  public void disable() {
    this.status = UserStatus.DISABLED;
  }

  public void lock(LocalDateTime until) {

    this.status = UserStatus.LOCKED;
    this.lockedUntil = until;
  }

  public void unlock() {

    this.status = UserStatus.ACTIVE;
    this.lockedUntil = null;
    this.failedLoginAttempts = 0;
  }

  public void incrementFailedLoginAttempts() {
    this.failedLoginAttempts++;
  }

  public void resetFailedLoginAttempts() {
    this.failedLoginAttempts = 0;
  }

  public void updateLastLogin() {
    this.lastLoginAt = LocalDateTime.now();
  }

  public void changePassword(String passwordHash) {

    this.passwordHash = passwordHash;
    this.lastPasswordChangedAt = LocalDateTime.now();
  }

  public boolean isActive() {
    return status == UserStatus.ACTIVE;
  }

  public boolean isLocked() {

    if (status != UserStatus.LOCKED) {
      return false;
    }

    return lockedUntil != null &&
        lockedUntil.isAfter(LocalDateTime.now());
  }

  public boolean isPendingVerification() {
    return status == UserStatus.PENDING_VERIFICATION;
  }

  public boolean isDisabled() {
    return status == UserStatus.DISABLED;
  }

}