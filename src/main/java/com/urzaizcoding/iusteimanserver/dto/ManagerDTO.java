package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.urzaizcoding.iusteimanserver.domain.Sex;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ManagerDTO {
	
	private final Long id;
	@NotNull
	private final String firstName;

	private final String lastName;
	@NotNull
	private final LocalDate birthDate;
	@NotNull
	private final Sex sex;
	@NotNull
	private final String birthPlace;
	@NotNull
	private final String country;
	@NotNull
	private final String contact;
	@NotNull @Email
	private final String email;
	
	private final Long account;
	
	
	@Builder
	public ManagerDTO(Long id, String firstName, String lastName, LocalDate birthDate, Sex sex, String birthPlace,
			String country, String contact, String email, Long account) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.sex = sex;
		this.birthPlace = birthPlace;
		this.country = country;
		this.contact = contact;
		this.email = email;
		this.account = account;
	}
	
	
}
