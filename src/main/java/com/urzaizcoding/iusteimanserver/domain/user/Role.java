package com.urzaizcoding.iusteimanserver.domain.user;

public enum Role {
	
	ADMINISTRATOR("A"),
	
	STUDENT("S");
	
	private String code;

	private Role(String code) {
		this.code = code;
	}

	public final String getCode() {
		return code;
	}
	
	
}
