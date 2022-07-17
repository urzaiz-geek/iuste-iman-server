package com.urzaizcoding.iusteimanserver.configuration;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.cloudinary.Cloudinary;

@Configuration
public class AppConfigurer {
	
	private final CloudinaryConfig cloudinaryConfig;
	
	
	public AppConfigurer(CloudinaryConfig cloudinaryConfig) {
		this.cloudinaryConfig = cloudinaryConfig;
	}

	@Bean
	ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		mapper.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setAmbiguityIgnored(true);
		
		

		return mapper;
	}
	
	@Bean
	FreeMarkerConfigurer freeMarkerConfigurationBean() {
		FreeMarkerConfigurer bean = new FreeMarkerConfigurer();
		bean.setDefaultEncoding("UTF-8");
		bean.setTemplateLoaderPath("classpath:/templates");
		
		return bean;
	}
	
	public static ZoneId appTimeZoneId() {
		return ZoneId.of("UTC");
	}
	
	@Bean
	Cloudinary cloudinary() {
		Cloudinary cloudinary = null;
		
		Map<String,String> config = new HashMap<>();
		
		config.put("cloud_name", cloudinaryConfig.getCloudName());
		config.put("api_key", cloudinaryConfig.getApiKey());
		config.put("api_secret", cloudinaryConfig.getApiSecret());
		
		cloudinary = new Cloudinary(config);
		
		return cloudinary;
	}
}
