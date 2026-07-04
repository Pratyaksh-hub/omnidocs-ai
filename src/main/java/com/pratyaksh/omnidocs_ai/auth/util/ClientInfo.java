package com.pratyaksh.omnidocs_ai.auth.util;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientInfo {

  private final String deviceName;
  private final String browser;
  private final String operatingSystem;
  private final String ipAddress;
  private final String userAgent;

}