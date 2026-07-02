package com.pratyaksh.omnidocs_ai.workspace.service.query;

import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.exception.WorkspaceNotFoundException;
import com.pratyaksh.omnidocs_ai.workspace.mapper.WorkspaceMapper;
import com.pratyaksh.omnidocs_ai.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    return workspaceRepository.findByUuidAndDeletedFalse(workspaceUuid)
        .orElseThrow(() -> new WorkspaceNotFoundException(workspaceUuid));
  }

  @Override
  public Page<WorkspaceResponse> getAll(Pageable pageable) {

    return workspaceRepository.findAllByDeletedFalse(pageable)
        .map(workspaceMapper::toResponse);
  }
}