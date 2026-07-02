package com.pratyaksh.omnidocs_ai.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GeminiConfig {

  @Bean
  public RestClient restClient(RestClient.Builder builder) {
    return builder.build();
  }

}