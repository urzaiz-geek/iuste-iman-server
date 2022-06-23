package com.urzaizcoding.iusteimanserver.domain.registration.student;

public enum ParentAttribute {
	FATHER("F"),MOTHER("M"),TUTOR("T");
	
	private String code;

	private ParentAttribute(String code) {
		this.code = code;
	}

	public final String getCode() {
		return code;
	}

}
