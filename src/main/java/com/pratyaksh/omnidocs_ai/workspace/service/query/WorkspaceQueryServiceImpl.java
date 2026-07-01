package com.pratyaksh.omnidocs_ai.workspace.service.query;

import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.exception.WorkspaceNotFoundException;
import com.pratyaksh.omnidocs_ai.workspace.mapper.WorkspaceMapper;
import com.pratyaksh.omnidocs_ai.workspace.repository.WorkspaceRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceQueryServiceImpl implements WorkspaceQueryService {

  private final WorkspaceRepository workspaceRepository;
  private final WorkspaceMapper workspaceMapper;

  @Override
  public WorkspaceResponse get(UUID workspaceUuid) {
    return workspaceMapper.toResponse(getEntity(workspaceUuid));
  }

  @Override
  public Workspace getEntity(UUID workspaceUuid) {
    return workspaceRepository.findByUuid(workspaceUuid)
        .orElseThrow(() -> new WorkspaceNotFoundException(workspaceUuid));
  }
}