package com.pratyaksh.omnidocs_ai.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE) // Forces this to run BEFORE Spring Security filters
  public CorsFilter corsFilter() {
    CorsConfiguration configuration = new CorsConfiguration();

    // Match your local and production environments perfectly
    configuration.setAllowedOriginPatterns(List.of(
        "http://localhost:3000",
        "https://*.vercel.app"
    ));

    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(List.of("Content-Disposition", "X-Access-Token-Expires-At"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return new CorsFilter(source);
  }
}