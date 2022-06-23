package com.urzaizcoding.iusteimanserver.domain.registration.student;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SequenceGenerators;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.urzaizcoding.iusteimanserver.domain.Person;
import com.urzaizcoding.iusteimanserver.domain.Sex;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity(name = "Student")
@Table(name = "student", uniqueConstraints = {
		@UniqueConstraint(columnNames = "registrationId", name = "RegistrationIdUniqueConstraint") })
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Student extends Person {

	@Getter(AccessLevel.NONE)
	private static final String STUDENT_SEQUENCE = "student_sequence";

	@Id
	@SequenceGenerators(@SequenceGenerator(name = STUDENT_SEQUENCE, sequenceName = STUDENT_SEQUENCE, allocationSize = 1))
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = STUDENT_SEQUENCE)
	@Column(name = "student_id")
	private Long studentId;

	@Column(nullable = true, length = 15)
	private String registrationId;

	@Column(nullable = false, length = 80)
	private String regionOfOrigin;

	@Column(nullable = false, length = 80)
	private String entranceDiploma;

	@Column(nullable = false, length = 4)
	private String yearOfGraduation;

	@Column(nullable = false, length = 80)
	private String schoolOfGraduation;

	@Column(nullable = false, length = 50)
	private String diplomaOption;

	@Column(nullable = false, length = 80)
	private String countryOfGraduation;

	@Column(nullable = false, length = 4)
	private LanguageLevel frenchLevel;

	@Column(nullable = false, length = 4)
	private LanguageLevel englishLevel;

	private String photoPath;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<Parent> parents;

	@Builder
	public Student(Long id, String firstName, String lastName, LocalDate birthDate, Sex sex, String birthPlace,
			String country, String contact, String email, Long studentId, String registrationId, String regionOfOrigin,
			String entranceDiploma, String yearOfGraduation, String schoolOfGraduation, String diplomaOption,
			String countryOfGraduation, LanguageLevel frenchLevel, LanguageLevel englishLevel, String photoPath) {
		super(id, firstName, lastName, birthDate, sex, birthPlace, country, contact, email);
		this.studentId = studentId;
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
		
		parents = new HashSet<>();
	}
	
	public void addParent(Parent parent) {
		if(parent != null) {
			parents.add(parent);
		}
	}

}
