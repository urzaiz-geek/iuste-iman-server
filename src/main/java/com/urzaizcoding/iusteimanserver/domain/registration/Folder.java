package com.urzaizcoding.iusteimanserver.domain.registration;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Folder")
@Table(name = "folder", uniqueConstraints = {
		@UniqueConstraint(columnNames = "folderRegistrationNumber", name = "FolderRegistrationNumberUniqueConstraint") })

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Folder {

	@Getter(AccessLevel.NONE)
	private static final String FOLDER_SEQUENCE = "folder_sequence";

	@Id
	@SequenceGenerator(name = FOLDER_SEQUENCE, sequenceName = FOLDER_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = FOLDER_SEQUENCE)
	@Column(name = "folder_id")
	private Long id;

	@Column(nullable = false, length = 15)
	private String folderRegistrationNumber;

	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate creationDate;

	@Column(columnDefinition = "DATE")
	private LocalDate depositDate;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<Part> parts;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.EAGER)
	private Form form;

	@OneToOne(mappedBy = "folder")
	private Student student;

	@ManyToOne
	private Course course;

	public Folder() {
		super();
		this.parts = new HashSet<>();
	}

	public Part newPart() {
		Part part = new Part();
		parts.add(part);
		return part;
	}
	
	public Quitus newQuitus() {
		Quitus quitus = new Quitus();
		parts.add(quitus);
		return quitus;
	}
}
