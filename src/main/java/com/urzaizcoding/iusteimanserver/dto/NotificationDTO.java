package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationDTO {
	private final Long id;

	private final String subject;

	private final LocalDate issuedDate;

	private final Boolean read;

	@Builder
	public NotificationDTO(Long id, String subject, LocalDate issuedDate, Boolean read) {
		super();
		this.id = id;
		this.subject = subject;
		this.issuedDate = issuedDate;
		this.read = read;
	}
	
	
}
