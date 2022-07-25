package com.urzaizcoding.iusteimanserver.configuration;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.cloudinary.Cloudinary;
import com.urzaizcoding.iusteimanserver.utils.cache.OTPCache;

@Configuration
public class AppConfigurer {
	
	private final CloudinaryConfig cloudinaryConfig;
	
	@Value("apisecretkey")
	private String apiSecret;
	
	@Value("jwtissuer")
	private String jwtIssuer;
	
	
	public String getJwtIssuer() {
		return jwtIssuer;
	}

	public AppConfigurer(CloudinaryConfig cloudinaryConfig) {
		this.cloudinaryConfig = cloudinaryConfig;
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
	
	public String getSecretKey() {
		return apiSecret;
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
	
	@Bean
	OTPCache otpCache() {
		return new OTPCache(5);	//otp expires in 5 minits
	}
}
