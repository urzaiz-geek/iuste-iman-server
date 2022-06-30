package com.urzaizcoding.iusteimanserver.domain.registration.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Parent")
@Table(name = "parent")
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
	@Column(length = 1)
	private ParentAttribute attribute;

	@Column(length = 100)
	private String place;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person_student_id",referencedColumnName = "person_id")
	private Student student;

	@Builder
	public Parent(Long id, String names, String job, String contact, String regionOfOrigin, ParentAttribute attribute,
			String place, Student student) {
		super();
		this.id = id;
		this.names = names;
		this.job = job;
		this.contact = contact;
		this.regionOfOrigin = regionOfOrigin;
		this.attribute = attribute;
		this.place = place;
		this.student = student;
	}
	
	

}
