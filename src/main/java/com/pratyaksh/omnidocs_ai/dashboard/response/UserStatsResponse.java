package com.pratyaksh.omnidocs_ai.dashboard.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserStatsResponse {

  private long totalWorkspaces;

  private long totalDocuments;

}