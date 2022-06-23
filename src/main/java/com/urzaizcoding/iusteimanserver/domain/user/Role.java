package com.urzaizcoding.iusteimanserver.domain.user;

public enum Role {
	
	ADMINISTRATOR("A");
	
	private String code;

	private Role(String code) {
		this.code = code;
	}

	public final String getCode() {
		return code;
	}
	
	
}
