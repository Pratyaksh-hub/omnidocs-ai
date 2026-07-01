package com.pratyaksh.omnidocs_ai.workspace.service.command;

import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import java.util.UUID;

public interface WorkspaceCommandService {

  WorkspaceResponse create(CreateWorkspaceRequest request);

  WorkspaceResponse update(UUID workspaceUuid,
      UpdateWorkspaceRequest request);

  void delete(UUID workspaceUuid);

}