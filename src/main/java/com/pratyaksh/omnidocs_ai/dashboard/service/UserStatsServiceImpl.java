package com.pratyaksh.omnidocs_ai.dashboard.service;

import com.pratyaksh.omnidocs_ai.auth.security.CustomUserDetails;
import com.pratyaksh.omnidocs_ai.dashboard.entity.UserStatistics;
import com.pratyaksh.omnidocs_ai.dashboard.repository.UserStatsRepository;
import com.pratyaksh.omnidocs_ai.dashboard.response.UserStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserStatsServiceImpl implements UserStatsService {

  private final UserStatsRepository repository;

  @Override
  public UserStatsResponse getDashboard() {

    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails principal =
        (CustomUserDetails) authentication.getPrincipal();

    UserStatistics stats =
        repository.findByUser(principal.getUser())
            .orElseGet(() -> {

              UserStatistics dashboardStats = new UserStatistics();

              dashboardStats.setUser(principal.getUser());
              dashboardStats.setTotalWorkspaces(0);
              dashboardStats.setTotalDocuments(0);

              return dashboardStats;
            });

    return UserStatsResponse.builder()
        .totalWorkspaces(stats.getTotalWorkspaces())
        .totalDocuments(stats.getTotalDocuments())
        .build();
  }

}