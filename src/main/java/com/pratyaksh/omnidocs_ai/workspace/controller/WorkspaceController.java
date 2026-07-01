package com.pratyaksh.omnidocs_ai.workspace.controller;

import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

  private final WorkspaceService workspaceService;

  @PostMapping
  public ResponseEntity<ApiResponse<WorkspaceResponse>> create(
      @Valid @RequestBody CreateWorkspaceRequest request) {

    WorkspaceResponse response = workspaceService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response));
  }

  @GetMapping("/{workspaceUuid}")
  public ResponseEntity<ApiResponse<Workspace>> get(
      @PathVariable UUID workspaceUuid) {

    Workspace response = workspaceService.getEntity(workspaceUuid);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}