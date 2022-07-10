package com.urzaizcoding.iusteimanserver.domain.registration.course;

public enum FeesType {
	DEFAULT("D", "Frais à payer pour procéder à l'inscription"),
	WHEN_ACCEPTED("WA", "Frais à payer une fois le dossier accepté");

	private String code;
	private String description;

	private FeesType(String code,String description) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return description;
	}
	
	
}
