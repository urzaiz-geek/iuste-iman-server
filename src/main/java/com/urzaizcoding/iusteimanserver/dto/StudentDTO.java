package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.student.LanguageLevel;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentDTO {
	
	private final Long id;

	@NotNull
	private final String firstName;

	private final String lastName;

	@NotNull
	private  final LocalDate birthDate;
	
	@NotNull
	private  final Sex sex;
	
	@NotNull
	private String quarter;

	@NotNull
	private  final String birthPlace;

	@NotNull
	private  final String country;

	@NotNull
	private  final String contact;

	@NotNull @Email
	private  final String email;

	private  final String registrationId;

	@NotNull
	private  final String regionOfOrigin;

	@NotNull
	private  final String entranceDiploma;

	@NotNull
	@Size(max = 4)
	private  final String yearOfGraduation;

	@NotNull
	private  final String schoolOfGraduation;
	
	private  final String diplomaOption;

	@NotNull
	private  final String countryOfGraduation;

	@Valid @NotNull
	private  final LanguageLevel frenchLevel;

	@Valid @NotNull
	private  final LanguageLevel englishLevel;

	private  final String photoPath;

	private  final Set<ParentDTO> parents;
	
	

	@Builder
	private StudentDTO(Long id, String firstName, String lastName, LocalDate birthDate, Sex sex,
			String birthPlace, String country, String contact, String email, String registrationId,
			String regionOfOrigin, String entranceDiploma, String yearOfGraduation, String schoolOfGraduation,
			String diplomaOption, String countryOfGraduation, LanguageLevel frenchLevel, LanguageLevel englishLevel,
			String photoPath, Set<ParentDTO> parents) {
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
		this.registrationId = registrationId;
		this.regionOfOrigin = regionOfOrigin;
		this.entranceDiploma = entranceDiploma;
		this.yearOfGraduation = yearOfGraduation;
		this.schoolOfGraduation = schoolOfGraduation;
		this.diplomaOption = diplomaOption;
		this.countryOfGraduation = countryOfGraduation;
		this.frenchLevel = frenchLevel;
		this.englishLevel = englishLevel;
		this.photoPath = photoPath;
		this.parents = parents;
	}
	

}
