package com.pratyaksh.omnidocs_ai;

import com.pratyaksh.omnidocs_ai.ai.config.GeminiProperties;
import com.pratyaksh.omnidocs_ai.auth.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({GeminiProperties.class, JwtProperties.class})
public class OmnidocsAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmnidocsAiApplication.class, args);
	}

}
