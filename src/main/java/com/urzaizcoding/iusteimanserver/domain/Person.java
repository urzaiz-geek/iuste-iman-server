package com.urzaizcoding.iusteimanserver.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.urzaizcoding.iusteimanserver.domain.user.Account;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity(name = "Person")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email", name = "EMAIL_UNIQUE_CONSTRAINT"),
		@UniqueConstraint(columnNames = "contact", name = "CONTACT_UNIQUE_CONSTRAINT") })
@Data
@EqualsAndHashCode
public abstract class Person {

	@Getter(AccessLevel.NONE)
	protected static final String PERSON_SEQUENCE = "person_sequence";

	@Id
	@SequenceGenerator(name = PERSON_SEQUENCE, allocationSize = 1, sequenceName = PERSON_SEQUENCE)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PERSON_SEQUENCE)
	@Column(name = "person_id", updatable = false, nullable = false)
	protected Long id;

	@Column(nullable = false, length = 100)
	protected String firstName;

	@Column(length = 100)
	protected String lastName;
	@Column(nullable = false, columnDefinition = "DATE")
	protected LocalDate birthDate;
	@Column(nullable = false, length = 1, columnDefinition = "CHAR")
	protected Sex sex;
	@Column(nullable = false, length = 100)
	protected String birthPlace;
	@Column(nullable = false, length = 100)
	protected String country;
	@Column(nullable = false, length = 20)
	protected String contact;
	@Column(nullable = false, length = 100)
	protected String email;
	
	@OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	protected Account account;

	protected Person(Long id, String firstName, String lastName, LocalDate birthDate, Sex sex, String birthPlace,
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
