package com.pratyaksh.omnidocs_ai.workspace.mapper;

import com.pratyaksh.omnidocs_ai.common.mapper.MapperConfiguration;
import com.pratyaksh.omnidocs_ai.workspace.dto.WorkspaceResponse;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface WorkspaceMapper {

  WorkspaceResponse toResponse(Workspace workspace);

}