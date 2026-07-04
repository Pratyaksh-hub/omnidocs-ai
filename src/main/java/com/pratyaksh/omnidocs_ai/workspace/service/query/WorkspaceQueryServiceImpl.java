package com.pratyaksh.omnidocs_ai.workspace.service.query;

import com.pratyaksh.omnidocs_ai.auth.service.CurrentUserService;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.exception.WorkspaceNotFoundException;
import com.pratyaksh.omnidocs_ai.workspace.mapper.WorkspaceMapper;
import com.pratyaksh.omnidocs_ai.workspace.repository.WorkspaceRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceQueryServiceImpl
    implements WorkspaceQueryService {

  private final WorkspaceRepository workspaceRepository;
  private final WorkspaceMapper workspaceMapper;
  private final CurrentUserService currentUserService;

  @Override
  public WorkspaceResponse get(UUID workspaceUuid) {

    return workspaceMapper.toResponse(
        getEntity(workspaceUuid)
    );
  }

  @Override
  public Workspace getEntity(UUID workspaceUuid) {

    User currentUser =
        currentUserService.getCurrentUser();

    return workspaceRepository
        .findByUuidAndOwnerAndDeletedFalse(
            workspaceUuid,
            currentUser
        )
        .orElseThrow(() ->
            new WorkspaceNotFoundException(workspaceUuid)
        );
  }

  @Override
  public Page<WorkspaceResponse> getAll(
      Pageable pageable) {

    User currentUser =
        currentUserService.getCurrentUser();

    return workspaceRepository
        .findAllByOwnerAndDeletedFalse(
            currentUser,
            pageable
        )
        .map(workspaceMapper::toResponse);
  }
}