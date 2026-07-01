package com.pratyaksh.omnidocs_ai.workspace.service.command;

import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.mapper.WorkspaceMapper;
import com.pratyaksh.omnidocs_ai.workspace.repository.WorkspaceRepository;
import com.pratyaksh.omnidocs_ai.workspace.service.query.WorkspaceQueryService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceCommandServiceImpl implements WorkspaceCommandService {

  private final WorkspaceRepository workspaceRepository;
  private final WorkspaceMapper workspaceMapper;
  private final WorkspaceQueryService workspaceQueryService;

  @Override
  public WorkspaceResponse create(CreateWorkspaceRequest request) {

    Workspace workspace = workspaceMapper.toEntity(request);

    workspace = workspaceRepository.save(workspace);

    return workspaceMapper.toResponse(workspace);
  }

  @Override
  public WorkspaceResponse update(UUID workspaceUuid,
      UpdateWorkspaceRequest request) {

    Workspace workspace = workspaceQueryService.getEntity(workspaceUuid);

    workspaceMapper.updateEntity(request, workspace);

    workspace = workspaceRepository.save(workspace);

    return workspaceMapper.toResponse(workspace);
  }

  @Override
  public void delete(UUID workspaceUuid) {

    Workspace workspace =
        workspaceQueryService.getEntity(workspaceUuid);

    workspace.setDeleted(true);
    workspace.setDeletedAt(LocalDateTime.now());

    workspaceRepository.save(workspace);
  }
}