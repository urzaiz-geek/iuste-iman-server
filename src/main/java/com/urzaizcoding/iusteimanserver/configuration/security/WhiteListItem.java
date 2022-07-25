package com.urzaizcoding.iusteimanserver.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.Data;

@Data
public class WhiteListItem {
	private HttpMethod method;
	private AntPathRequestMatcher matcher;
	private String endPoint;

	public WhiteListItem(HttpMethod method, String endPoint) {
		super();
		this.method = method;
		this.endPoint = endPoint;
		matcher = new AntPathRequestMatcher(this.endPoint,method.name());
		
	}
	
	public boolean check(HttpServletRequest endPoint) {
		
		if(endPoint == null)
			return false;
		
		if(!matcher.matches(endPoint)) {
			return false;
		}
			
		return true;
	}
}
