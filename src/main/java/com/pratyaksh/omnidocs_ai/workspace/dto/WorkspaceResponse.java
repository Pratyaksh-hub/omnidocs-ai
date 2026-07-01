package com.pratyaksh.omnidocs_ai.workspace.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class WorkspaceResponse {

  private UUID uuid;

  private String name;

  private String description;

  private LocalDateTime createdAt;

}