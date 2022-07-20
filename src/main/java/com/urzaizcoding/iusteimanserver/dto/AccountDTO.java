package com.urzaizcoding.iusteimanserver.dto;

import com.urzaizcoding.iusteimanserver.domain.user.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDTO {
	private Long id;

	private String username;

	private String creationDate;

	private String lastConnectionDate;

	private boolean active;

	private Role role;

	private AccountDTO creator;

	@Builder
	public AccountDTO(Long id, String username, String creationDate, String lastConnectionDate, boolean active,
			Role role, AccountDTO creator) {
		super();
		this.id = id;
		this.username = username;
		this.creationDate = creationDate;
		this.lastConnectionDate = lastConnectionDate;
		this.active = active;
		this.role = role;
		this.creator = creator;
	}

}
