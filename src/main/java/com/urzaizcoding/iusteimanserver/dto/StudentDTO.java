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
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentDTO {
	
	private Long id;

	@NotNull
	private String firstName;

	private String lastName;

	@NotNull
	private  LocalDate birthDate;
	
	@NotNull
	private  Sex sex;

	@NotNull
	private  String birthPlace;

	@NotNull
	private  String country;

	@NotNull
	private  String contact;

	@NotNull @Email
	private  String email;

	private  String registrationId;

	@NotNull
	private  String regionOfOrigin;

	@NotNull
	private  String entranceDiploma;

	@NotNull
	@Size(max = 4)
	private  String yearOfGraduation;

	@NotNull
	private  String schoolOfGraduation;
	
	private  String diplomaOption;

	@NotNull
	private  String countryOfGraduation;

	@Valid @NotNull
	private  LanguageLevel frenchLevel;

	@Valid @NotNull
	private  LanguageLevel englishLevel;

	private  String photoPath;

	private  Set<ParentDTO> parents;
	
	

	@Builder
	private StudentDTO(Long studentId, String firstName, String lastName, LocalDate birthDate, Sex sex,
			String birthPlace, String country, String contact, String email, String registrationId,
			String regionOfOrigin, String entranceDiploma, String yearOfGraduation, String schoolOfGraduation,
			String diplomaOption, String countryOfGraduation, LanguageLevel frenchLevel, LanguageLevel englishLevel,
			String photoPath, Set<ParentDTO> parents) {
		super();
		this.id = studentId;
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
