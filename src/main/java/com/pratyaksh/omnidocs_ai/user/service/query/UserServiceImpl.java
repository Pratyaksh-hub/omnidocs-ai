package com.pratyaksh.omnidocs_ai.user.service.query;

import com.pratyaksh.omnidocs_ai.auth.service.CurrentUserService;
import com.pratyaksh.omnidocs_ai.user.dto.response.UserProfileResponse;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import com.pratyaksh.omnidocs_ai.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  private final CurrentUserService currentUserService;
  private final UserMapper userMapper;

  @Override
  public UserProfileResponse getProfile() {

    User user = currentUserService.getCurrentUser();

    return userMapper.toProfileResponse(user);
  }
}