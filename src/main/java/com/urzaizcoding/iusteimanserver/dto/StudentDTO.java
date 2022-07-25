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
	private final LocalDate birthDate;

	@NotNull
	private final Sex sex;

	@NotNull
	private final String birthPlace;

	@NotNull
	private final String country;

	@NotNull
	private final String contact;

	@NotNull
	@Email
	private final String email;

	@NotNull
	private String quarter;
	private final String registrationId;

	@NotNull
	private final String regionOfOrigin;

	@NotNull
	private final String entranceDiploma;

	@NotNull
	@Size(max = 4)
	private final String yearOfGraduation;

	@NotNull
	private final String schoolOfGraduation;

	@NotNull
	private final String countryOfGraduation;

	@Valid
	@NotNull
	private final LanguageLevel frenchLevel;

	@Valid
	@NotNull
	private final LanguageLevel englishLevel;

	private final String photoPath;

	private final Set<ParentDTO> parents;
	
	private final Long account;

	@Builder
	private StudentDTO(Long id, @NotNull String firstName, String lastName, @NotNull LocalDate birthDate,
			@NotNull Sex sex, @NotNull String quarter, @NotNull String birthPlace, @NotNull String country,
			@NotNull String contact, @NotNull @Email String email, String registrationId,
			@NotNull String regionOfOrigin, @NotNull String entranceDiploma,
			@NotNull @Size(max = 4) String yearOfGraduation, @NotNull String schoolOfGraduation,
			@NotNull String countryOfGraduation, @Valid @NotNull LanguageLevel frenchLevel,
			@Valid @NotNull LanguageLevel englishLevel, String photoPath,
			Set<ParentDTO> parents, Long account) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.sex = sex;
		this.quarter = quarter;
		this.birthPlace = birthPlace;
		this.country = country;
		this.contact = contact;
		this.email = email;
		this.registrationId = registrationId;
		this.regionOfOrigin = regionOfOrigin;
		this.entranceDiploma = entranceDiploma;
		this.yearOfGraduation = yearOfGraduation;
		this.schoolOfGraduation = schoolOfGraduation;
		this.countryOfGraduation = countryOfGraduation;
		this.frenchLevel = frenchLevel;
		this.englishLevel = englishLevel;
		this.photoPath = photoPath;
		this.parents = parents;
		this.account = account;
	}

}
