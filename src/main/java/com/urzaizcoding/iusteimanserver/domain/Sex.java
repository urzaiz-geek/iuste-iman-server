package com.urzaizcoding.iusteimanserver.domain;

public enum Sex {
	MALE("M"),FEMALE("F"),OTHER("O");
	
	private Sex(String code) {
		this.code = code;
	}

	private final String code;

	public final String getCode() {
		return code;
	}
}
