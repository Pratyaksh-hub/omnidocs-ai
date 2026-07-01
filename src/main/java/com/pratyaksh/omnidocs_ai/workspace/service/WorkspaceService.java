package com.pratyaksh.omnidocs_ai.workspace.service;

import com.pratyaksh.omnidocs_ai.document.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import java.util.UUID;

public interface WorkspaceService {

  WorkspaceResponse create(CreateWorkspaceRequest request);

  WorkspaceResponse get(UUID workspaceUuid);

  Workspace getEntity(UUID workspaceUuid);
}