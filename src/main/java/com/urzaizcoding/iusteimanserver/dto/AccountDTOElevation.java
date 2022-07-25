package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class AccountDTOElevation {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String otp;
}
