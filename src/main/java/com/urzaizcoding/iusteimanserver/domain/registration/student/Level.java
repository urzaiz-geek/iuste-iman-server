package com.urzaizcoding.iusteimanserver.domain.registration.student;

import java.util.Arrays;

public enum Level {
	NOT_AT_ALL("N"),LITTLE("L"),MEDIUM("M"),GOOD("G");
	
	
	private Level(String code) {
		this.code = code;
	}
	
	private String code;



	public final String getCode() {
		return code;
	}
	
	public static Level of(String code) {
		return Arrays.stream(Level.values()).filter(l -> l.getCode().equals(code)).findFirst().orElse(NOT_AT_ALL);
	}
}
