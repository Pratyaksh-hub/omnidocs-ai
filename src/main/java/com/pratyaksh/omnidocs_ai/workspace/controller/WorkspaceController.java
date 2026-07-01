package com.pratyaksh.omnidocs_ai.workspace.controller;

import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import com.pratyaksh.omnidocs_ai.common.response.PageResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentSummaryResponse;
import com.pratyaksh.omnidocs_ai.document.facade.DocumentFacade;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.facade.WorkspaceFacede;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

  private final WorkspaceFacede workspaceFacede;
  private final DocumentFacade documentFacade;

  @PostMapping
  public ResponseEntity<ApiResponse<WorkspaceResponse>> create(
      @Valid @RequestBody CreateWorkspaceRequest request) {

    WorkspaceResponse response = workspaceFacede.createWorkspace(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response));
  }

  @GetMapping("/{workspaceUuid}")
  public ResponseEntity<ApiResponse<WorkspaceResponse>> get(
      @PathVariable UUID workspaceUuid) {

    WorkspaceResponse response =
        workspaceFacede.getWorkspace(workspaceUuid);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/{workspaceUuid}/documents")
  public ResponseEntity<ApiResponse<PageResponse<DocumentSummaryResponse>>> getDocuments(
      @PathVariable UUID workspaceUuid,
      Pageable pageable) {

    return ResponseEntity.ok(
        ApiResponse.success(
            documentFacade.getDocuments(
                workspaceUuid,
                pageable
            )
        )
    );
  }
}