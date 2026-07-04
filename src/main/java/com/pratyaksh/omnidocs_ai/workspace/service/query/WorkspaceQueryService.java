package com.pratyaksh.omnidocs_ai.workspace.service.query;

import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkspaceQueryService {

  WorkspaceResponse get(UUID workspaceUuid);

  Workspace getEntity(UUID workspaceUuid);

  Page<WorkspaceResponse> getAll(Pageable pageable);

}