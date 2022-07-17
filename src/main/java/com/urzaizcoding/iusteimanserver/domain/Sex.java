package com.urzaizcoding.iusteimanserver.domain;

public enum Sex implements HasFrenchValue{
	MALE("M","MASCULIN"),FEMALE("F","FEMININ"),OTHER("O","AUTRES");
	
	private Sex(String code,String value) {
		this.code = code;
		this.value = value;
	}

	private final String code;
	private String value;

	public final String getCode() {
		return code;
	}

	@Override
	public String frenchValue() {
		return value;
	}
}
