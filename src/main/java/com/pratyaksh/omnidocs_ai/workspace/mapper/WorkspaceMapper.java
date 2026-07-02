package com.pratyaksh.omnidocs_ai.workspace.mapper;

import com.pratyaksh.omnidocs_ai.common.mapper.MapperConfiguration;
import com.pratyaksh.omnidocs_ai.workspace.dto.CreateWorkspaceRequest;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(config = MapperConfiguration.class)
public interface WorkspaceMapper {

  WorkspaceResponse toResponse(Workspace workspace);

  @ObjectFactory
  default Workspace toEntity(CreateWorkspaceRequest request) {
    return Workspace.create(
        request.getName(),
        request.getDescription()
    );
  }
}