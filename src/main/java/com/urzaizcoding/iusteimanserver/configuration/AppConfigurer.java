package com.urzaizcoding.iusteimanserver.configuration;

import java.time.ZoneId;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class AppConfigurer {
	
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
}
