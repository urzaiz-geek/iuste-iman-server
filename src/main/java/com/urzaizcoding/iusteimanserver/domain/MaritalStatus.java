package com.urzaizcoding.iusteimanserver.domain;

public enum MaritalStatus{
	SINGLE("S"),
	MARRIED("M"),
	DIVORCED("D"),
	WIDOWED("W");

	
	private String code;
	
	
	
	private MaritalStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
