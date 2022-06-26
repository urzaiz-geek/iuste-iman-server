package com.urzaizcoding.iusteimanserver.domain.registration.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Parent")
@Table(name = "parent", uniqueConstraints = {
		@UniqueConstraint(columnNames = "contact", name = "ContactUniqueConstraint") })
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"student"})
@EqualsAndHashCode(exclude = {"student"})
public class Parent {
	@Getter(AccessLevel.NONE)
	private static final String PARENT_SEQUENCE = "parent_sequence";

	@Id
	@SequenceGenerator(name = PARENT_SEQUENCE, sequenceName = PARENT_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PARENT_SEQUENCE)
	@Column(name = "parent_id")
	private Long id;

	@Column(length = 255)
	private String names;
	@Column(length = 100)
	private String job;
	@Column(length = 15)
	private String contact;

	@Column(length = 100)
	private String regionOfOrigin;
	@Column(length = 1, unique = true)
	private ParentAttribute attribute;

	@Column(length = 100)
	private String place;
	
	@ManyToOne
	@JsonIgnore
	private Student student;

}
