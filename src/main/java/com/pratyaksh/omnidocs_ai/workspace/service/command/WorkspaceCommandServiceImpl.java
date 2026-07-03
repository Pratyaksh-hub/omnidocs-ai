package com.pratyaksh.omnidocs_ai.workspace.service.command;

import com.pratyaksh.omnidocs_ai.auth.service.CurrentUserService;
import com.pratyaksh.omnidocs_ai.dashboard.repository.UserStatsRepository;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.mapper.WorkspaceMapper;
import com.pratyaksh.omnidocs_ai.workspace.repository.WorkspaceRepository;
import com.pratyaksh.omnidocs_ai.workspace.service.query.WorkspaceQueryService;
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
  private final UserStatsRepository userStatsRepository;
  private final CurrentUserService currentUserService;

  @Override
  @Transactional
  public WorkspaceResponse create(CreateWorkspaceRequest request) {

    User user = currentUserService.getCurrentUser();

    Workspace workspace = Workspace.create(
        user,
        request.getName(),
        request.getDescription()
    );

    workspace = workspaceRepository.save(workspace);

    userStatsRepository.incrementWorkspaceCount(
        user.getId()
    );

    return workspaceMapper.toResponse(workspace);
  }

  @Override
  public WorkspaceResponse update(UUID workspaceUuid,
      UpdateWorkspaceRequest request) {

    Workspace workspace = workspaceQueryService.getEntity(workspaceUuid);

    workspace.update(
        request.getName(),
        request.getDescription()
    );

    return workspaceMapper.toResponse(workspace);
  }

  @Override
  public void delete(UUID workspaceUuid) {

    Workspace workspace = workspaceQueryService.getEntity(workspaceUuid);

    workspace.markDeleted();

    User user = currentUserService.getCurrentUser();
    userStatsRepository.decrementWorkspaceCount(user.getId());
  }
}