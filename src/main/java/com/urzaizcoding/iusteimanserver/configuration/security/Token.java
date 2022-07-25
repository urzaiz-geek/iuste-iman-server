package com.urzaizcoding.iusteimanserver.configuration.security;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {
	private final String refreshToken;
	private final String accessToken;
	
	@Builder
	public Token(String refreshToken, String accessToken) {
		super();
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
	}
	
	
}
