package com.urzaizcoding.iusteimanserver.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountDTOStatus {
	
	private boolean active;

	@Builder
	public AccountDTOStatus(boolean active) {
		super();
		this.active = active;
	}
	
	
}
