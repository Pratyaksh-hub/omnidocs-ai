package com.pratyaksh.omnidocs_ai.audit.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.pratyaksh.omnidocs_ai.audit.entity.AuditAction;
import com.pratyaksh.omnidocs_ai.audit.entity.AuditStatus;
import com.pratyaksh.omnidocs_ai.user.entity.User;
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
public class AuditLogRequest {

  private User user;

  private AuditAction action;

  private String resourceType;

  private UUID resourceUuid;

  private AuditStatus status;

  private String ipAddress;

  private String userAgent;

  private JsonNode metadata;

}