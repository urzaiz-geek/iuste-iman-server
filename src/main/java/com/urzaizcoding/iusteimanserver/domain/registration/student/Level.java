package com.urzaizcoding.iusteimanserver.domain.registration.student;

import java.util.Arrays;

import com.urzaizcoding.iusteimanserver.domain.HasFrenchValue;

public enum Level implements HasFrenchValue {
	NOT_AT_ALL("N", "PAS DU TOUT"),
	LITTLE("L", "UN PEU"),
	MEDIUM("M", "MOYEN"),
	GOOD("G", "EXCELLENT");

	private Level(String code, String value) {
		this.code = code;
		this.value = value;
	}

	private String code;
	private String value;

	public final String getCode() {
		return code;
	}

	public static Level of(String code) {
		return Arrays.stream(Level.values()).filter(l -> l.getCode().equals(code)).findFirst().orElse(NOT_AT_ALL);
	}

	@Override
	public String frenchValue() {
		return value;
	}
}
