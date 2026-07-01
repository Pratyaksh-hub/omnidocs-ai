package com.pratyaksh.omnidocs_ai.workspace.mapper;

import com.pratyaksh.omnidocs_ai.common.mapper.MapperConfiguration;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface WorkspaceMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  Workspace toEntity(CreateWorkspaceRequest request);

  WorkspaceResponse toResponse(Workspace workspace);

}