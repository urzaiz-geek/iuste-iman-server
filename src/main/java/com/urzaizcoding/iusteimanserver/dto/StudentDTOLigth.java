package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import com.urzaizcoding.iusteimanserver.domain.Sex;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentDTOLigth {
	private final Long id;

	private final String firstName;

	private final String lastName;

	private final LocalDate birthDate;

	private final Sex sex;

	private final String birthPlace;

	private final String country;

	private final String contact;

	private final String email;

	@Builder
	public StudentDTOLigth(Long id, String firstName, String lastName, LocalDate birthDate, Sex sex, String birthPlace,
			String country, String contact, String email) {
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
	}
	
	

}
