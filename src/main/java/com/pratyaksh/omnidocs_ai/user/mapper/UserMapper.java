package com.pratyaksh.omnidocs_ai.user.mapper;

import com.pratyaksh.omnidocs_ai.common.mapper.MapperConfiguration;
import com.pratyaksh.omnidocs_ai.user.dto.response.UserProfileResponse;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

  @Mapping(target = "userUuid", source = "uuid")
  @Mapping(target = "role", expression = "java(user.getRole().name())")
  UserProfileResponse toProfileResponse(User user);

}