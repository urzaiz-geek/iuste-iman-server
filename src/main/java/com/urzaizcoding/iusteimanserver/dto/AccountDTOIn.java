package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.urzaizcoding.iusteimanserver.domain.user.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDTOIn {

	@NotBlank
	private final String username;
	@NotBlank
	private final String password;

	private final boolean active;
	
	@NotNull
	private Role role;
	
	private final Long ownerId;

	@Builder
	public AccountDTOIn(@NotBlank String username, @NotBlank String password, boolean active,
			@NotNull Role role, Long owner) {
		super();
		this.username = username;
		this.password = password;
		this.active = active;
		this.role = role;
		this.ownerId = owner;
	}
	

}
