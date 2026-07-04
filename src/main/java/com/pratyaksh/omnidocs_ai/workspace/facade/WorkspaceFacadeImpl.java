package com.pratyaksh.omnidocs_ai.workspace.facade;

import com.pratyaksh.omnidocs_ai.common.response.PageResponse;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.RenameWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.service.command.WorkspaceCommandService;
import com.pratyaksh.omnidocs_ai.workspace.service.query.WorkspaceQueryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceFacadeImpl implements WorkspaceFacade {

  private final WorkspaceCommandService workspaceCommandService;
  private final WorkspaceQueryService workspaceQueryService;

  @Override
  public WorkspaceResponse createWorkspace(CreateWorkspaceRequest request) {
    return workspaceCommandService.create(request);
  }

  @Override
  public WorkspaceResponse getWorkspace(UUID workspaceUuid) {
    return workspaceQueryService.get(workspaceUuid);
  }

  @Override
  public WorkspaceResponse updateWorkspace(UUID workspaceUuid,
      UpdateWorkspaceRequest request) {
    return workspaceCommandService.update(workspaceUuid, request);
  }

  @Override
  public void deleteWorkspace(UUID workspaceUuid) {
    workspaceCommandService.delete(workspaceUuid);
  }

  @Override
  public PageResponse<WorkspaceResponse> getAllWorkspaces(
      Pageable pageable) {

    Page<WorkspaceResponse> page =
        workspaceQueryService.getAll(pageable);

    return PageResponse.of(page);
  }

  @Override
  public WorkspaceResponse rename(
      UUID workspaceUuid,
      RenameWorkspaceRequest request
  ) {

    return workspaceCommandService.rename(workspaceUuid, request);
  }
}