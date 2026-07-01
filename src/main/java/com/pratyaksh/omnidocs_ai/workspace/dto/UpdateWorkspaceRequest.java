package com.pratyaksh.omnidocs_ai.workspace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkspaceRequest {

  @NotBlank
  private String name;

  private String description;

}