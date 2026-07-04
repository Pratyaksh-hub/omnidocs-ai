package com.pratyaksh.omnidocs_ai.ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "gemini")
public class GeminiProperties {

  private String apiKey;

  private String embeddingModel = "text-embedding-004";

  private String chatModel = "gemini-2.5-flash";

  private String baseUrl =
      "https://generativelanguage.googleapis.com";
}