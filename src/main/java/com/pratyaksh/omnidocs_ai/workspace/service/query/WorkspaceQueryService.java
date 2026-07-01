package com.pratyaksh.omnidocs_ai.workspace.service.query;

import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import java.util.UUID;

public interface WorkspaceQueryService {

  WorkspaceResponse get(UUID workspaceUuid);

  Workspace getEntity(UUID workspaceUuid);

}