package com.urzaizcoding.iusteimanserver.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")

@Getter
@Setter
public class CloudinaryConfig {
	private String cloudName;
	private String apiKey;
	private String apiSecret;
}
