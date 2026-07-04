package com.pratyaksh.omnidocs_ai.workspace.facade;

import com.pratyaksh.omnidocs_ai.common.response.PageResponse;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.RenameWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface WorkspaceFacade {

  WorkspaceResponse createWorkspace(CreateWorkspaceRequest request);

  WorkspaceResponse getWorkspace(UUID workspaceUuid);

  WorkspaceResponse updateWorkspace(UUID workspaceUuid,
      UpdateWorkspaceRequest request);

  void deleteWorkspace(UUID workspaceUuid);

  PageResponse<WorkspaceResponse> getAllWorkspaces(Pageable pageable);

  WorkspaceResponse rename(
      UUID workspaceUuid,
      RenameWorkspaceRequest request
  );
}