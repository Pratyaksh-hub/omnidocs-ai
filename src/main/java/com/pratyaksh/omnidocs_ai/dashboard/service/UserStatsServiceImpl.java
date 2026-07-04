package com.pratyaksh.omnidocs_ai.dashboard.service;

import com.pratyaksh.omnidocs_ai.auth.service.CurrentUserService;
import com.pratyaksh.omnidocs_ai.dashboard.entity.UserStatistics;
import com.pratyaksh.omnidocs_ai.dashboard.repository.UserStatsRepository;
import com.pratyaksh.omnidocs_ai.dashboard.response.UserStatsResponse;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserStatsServiceImpl implements UserStatsService {

  private final UserStatsRepository repository;
  private final CurrentUserService currentUserService;

  @Override
  public UserStatsResponse getDashboard() {

    User user = currentUserService.getCurrentUser();

    UserStatistics stats =
        repository.findByUser(user)
            .orElseGet(() -> {

              UserStatistics dashboardStats = new UserStatistics();

              dashboardStats.setUser(user);
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