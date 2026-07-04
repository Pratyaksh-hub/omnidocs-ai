package com.pratyaksh.omnidocs_ai.user.controller;

import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import com.pratyaksh.omnidocs_ai.user.dto.response.UserProfileResponse;
import com.pratyaksh.omnidocs_ai.user.service.query.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {

    return ResponseEntity.ok(
        ApiResponse.success(
            userService.getProfile()
        )
    );
  }
}