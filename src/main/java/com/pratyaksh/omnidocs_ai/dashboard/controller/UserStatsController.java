package com.pratyaksh.omnidocs_ai.dashboard.controller;

import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import com.pratyaksh.omnidocs_ai.dashboard.response.UserStatsResponse;
import com.pratyaksh.omnidocs_ai.dashboard.service.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class UserStatsController {

  private final UserStatsService userStatsService;

  @GetMapping
  public ResponseEntity<ApiResponse<UserStatsResponse>> getDashboard() {

    return ResponseEntity.ok(
        ApiResponse.success(
            userStatsService.getDashboard()
        )
    );
  }

}