package com.urzaizcoding.iusteimanserver.configuration.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	private final AppConfigurer configurer;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AppConfigurer configurer) {
		super();
		this.authenticationManager = authenticationManager;
		this.configurer = configurer;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		JSONObject credentials = null;
		
		try {
			//parse the request body to get the credentials
			credentials = new JSONObject(new BufferedReader(new InputStreamReader(request.getInputStream()))
					.lines().collect(Collectors.joining("\n")));
		} catch (Exception e) {
			throw new AuthenticationServiceException("Authentication failed", e);
		} 
		
		String username = "";
		String password = "";
		
		try {
			username = credentials.getString("username");
			password = credentials.getString("password");
		}catch (JSONException e) {
			throw new AuthenticationServiceException("Authentication failed", e);
		}
		
		
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		AppUserDetails user = (AppUserDetails) authResult.getPrincipal();

		JWTAuthenticationProvider provider = JWTAuthenticationProvider.getProvider(configurer);
		
		String accessToken = provider.createAccessToken(user, request.getRequestURL().toString());
		
		String refreshToken = provider.createRefreshToken(user.getUsername(), request.getRequestURL().toString());
		
		Map<String,String> idToken = new HashMap<>();
		
		idToken.put("accessToken",accessToken);
		idToken.put("refreshToken",refreshToken);
		
		response.setContentType("application/json");

		new ObjectMapper().writeValue(response.getOutputStream(), idToken);

	}

}
