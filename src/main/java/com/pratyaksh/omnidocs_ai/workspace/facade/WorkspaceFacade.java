package com.pratyaksh.omnidocs_ai.workspace.facade;

import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import java.util.UUID;

public interface WorkspaceFacade {

  WorkspaceResponse createWorkspace(CreateWorkspaceRequest request);

  WorkspaceResponse getWorkspace(UUID workspaceUuid);

  WorkspaceResponse updateWorkspace(UUID workspaceUuid,
      UpdateWorkspaceRequest request);

  void deleteWorkspace(UUID workspaceUuid);

}