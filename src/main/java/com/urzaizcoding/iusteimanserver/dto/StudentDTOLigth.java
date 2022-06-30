package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import com.urzaizcoding.iusteimanserver.domain.Sex;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTOLigth {
	private Long id;

	private String firstName;

	private String lastName;

	private LocalDate birthDate;

	private Sex sex;

	private String birthPlace;

	private String country;

	private String contact;

	private String email;

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
