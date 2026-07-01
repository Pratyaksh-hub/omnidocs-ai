package com.pratyaksh.omnidocs_ai.workspace.service.command;

import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.mapper.WorkspaceMapper;
import com.pratyaksh.omnidocs_ai.workspace.repository.WorkspaceRepository;
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

  @Override
  public WorkspaceResponse create(CreateWorkspaceRequest request) {

    Workspace workspace = workspaceMapper.toEntity(request);

    workspace = workspaceRepository.save(workspace);

    return workspaceMapper.toResponse(workspace);
  }

  @Override
  public WorkspaceResponse update(UUID workspaceUuid,
      UpdateWorkspaceRequest request) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Override
  public void delete(UUID workspaceUuid) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }
}