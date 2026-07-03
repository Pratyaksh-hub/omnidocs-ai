package com.pratyaksh.omnidocs_ai.auth.util;

import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientInfoExtractor {

  public ClientInfo extract(HttpServletRequest request) {

    String userAgentHeader =
        request.getHeader("User-Agent");

    UserAgent userAgent =
        UserAgent.parseUserAgentString(userAgentHeader);

    String browser =
        userAgent.getBrowser().getName();

    String os =
        userAgent.getOperatingSystem().getName();

    String ipAddress = extractIp(request);

    String device =
        browser + " on " + os;

    return ClientInfo.builder()
        .browser(browser)
        .operatingSystem(os)
        .deviceName(device)
        .ipAddress(ipAddress)
        .userAgent(userAgentHeader)
        .build();
  }

  private String extractIp(HttpServletRequest request) {

    String forwarded =
        request.getHeader("X-Forwarded-For");

    if (forwarded != null &&
        !forwarded.isBlank() &&
        !"unknown".equalsIgnoreCase(forwarded)) {

      return forwarded.split(",")[0].trim();
    }

    String realIp =
        request.getHeader("X-Real-IP");

    if (realIp != null &&
        !realIp.isBlank()) {
      return realIp;
    }

    return request.getRemoteAddr();
  }

}