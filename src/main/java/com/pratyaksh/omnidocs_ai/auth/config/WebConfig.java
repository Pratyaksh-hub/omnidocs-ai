package com.pratyaksh.omnidocs_ai.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("http://localhost:3000", "https://*.vercel.app")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .exposedHeaders("Content-Disposition", "X-Access-Token-Expires-At")
        .allowCredentials(true)
        .maxAge(3600); // Caches preflight response for 1 hour
  }
}