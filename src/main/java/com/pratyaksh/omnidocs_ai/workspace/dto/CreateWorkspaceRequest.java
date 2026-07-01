package com.pratyaksh.omnidocs_ai.workspace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWorkspaceRequest {

  @NotBlank
  @Size(max = 150)
  private String name;

  @Size(max = 1000)
  private String description;

}