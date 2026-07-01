package com.pratyaksh.omnidocs_ai.common.config;

import com.pratyaksh.omnidocs_ai.storage.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfiguration {
}