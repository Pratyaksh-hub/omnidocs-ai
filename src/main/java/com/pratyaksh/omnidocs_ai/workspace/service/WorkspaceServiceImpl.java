package com.pratyaksh.omnidocs_ai.workspace.service;

import com.pratyaksh.omnidocs_ai.document.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.exception.WorkspaceNotFoundException;
import com.pratyaksh.omnidocs_ai.workspace.mapper.WorkspaceMapper;
import com.pratyaksh.omnidocs_ai.workspace.repository.WorkspaceRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

  private final WorkspaceRepository workspaceRepository;
  private final WorkspaceMapper workspaceMapper;

  @Override
  public WorkspaceResponse create(CreateWorkspaceRequest request) {

    Workspace workspace = workspaceMapper.toEntity(request);

    workspace = workspaceRepository.save(workspace);

    return workspaceMapper.toResponse(workspace);
  }

  @Override
  @Transactional(readOnly = true)
  public WorkspaceResponse get(UUID workspaceUuid) {

    return workspaceMapper.toResponse(getEntity(workspaceUuid));
  }

  @Override
  @Transactional(readOnly = true)
  public Workspace getEntity(UUID workspaceUuid) {

    return workspaceRepository.findByUuid(workspaceUuid)
        .orElseThrow(() ->
            new WorkspaceNotFoundException(workspaceUuid));
  }
}