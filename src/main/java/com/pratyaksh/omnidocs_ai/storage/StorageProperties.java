package com.pratyaksh.omnidocs_ai.storage;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
@Validated
public class StorageProperties {

  /**
   * Root directory where physical files are stored.
   */
  @NotBlank
  private String location;
}
