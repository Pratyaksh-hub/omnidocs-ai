package com.pratyaksh.omnidocs_ai.workspace.mapper;

import com.pratyaksh.omnidocs_ai.common.mapper.MapperConfiguration;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.UpdateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface WorkspaceMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  Workspace toEntity(CreateWorkspaceRequest request);

  WorkspaceResponse toResponse(Workspace workspace);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  void updateEntity(UpdateWorkspaceRequest request,
      @MappingTarget Workspace workspace);
}