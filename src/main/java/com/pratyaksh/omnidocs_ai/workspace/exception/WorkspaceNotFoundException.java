package com.pratyaksh.omnidocs_ai.workspace.exception;

import com.pratyaksh.omnidocs_ai.common.exception.BusinessException;
import com.pratyaksh.omnidocs_ai.common.exception.ErrorCode;

import java.util.UUID;

public class WorkspaceNotFoundException extends BusinessException {

  public WorkspaceNotFoundException(UUID workspaceUuid) {
    super(
        ErrorCode.WORKSPACE_NOT_FOUND,
        "Workspace not found with UUID: " + workspaceUuid
    );
  }

}