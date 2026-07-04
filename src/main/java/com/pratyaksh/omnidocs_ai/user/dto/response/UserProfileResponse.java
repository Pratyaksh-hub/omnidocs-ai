package com.pratyaksh.omnidocs_ai.user.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

  private UUID userUuid;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
  private boolean active;
  private boolean emailVerified;
//  private String tier;
//  private boolean subscriptionActive;
//  private LocalDate subscriptionStartDate;
//  private LocalDate subscriptionEndDate;
//  private long storageUsed;
//  private long storageLimit;
//  private long maxUploadSize;
  private LocalDateTime lastLoginAt;
  private LocalDateTime createdAt;

}