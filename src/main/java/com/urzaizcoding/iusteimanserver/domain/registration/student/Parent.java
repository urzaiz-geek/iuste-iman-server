package com.urzaizcoding.iusteimanserver.domain.registration.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
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
@ToString
@EqualsAndHashCode
public class Parent {
	@Getter(AccessLevel.NONE)
	private static final String PARENT_SEQUENCE = "parent_sequence";

	@Id
	@SequenceGenerator(name = PARENT_SEQUENCE, sequenceName = PARENT_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PARENT_SEQUENCE)
	@Column(name = "person_id")
	private Long id;

	@Column(nullable = false, length = 255)
	private String names;
	@Column(nullable = false, length = 100)
	private String job;
	@Column(nullable = false, length = 15)
	private String contact;

	@Column(nullable = false, length = 100)
	private String regionOfOrigin;
	@Column(nullable = false, length = 1)
	private ParentAttribute attribute;

	@Column(nullable = false, length = 100)
	private String place;

}
