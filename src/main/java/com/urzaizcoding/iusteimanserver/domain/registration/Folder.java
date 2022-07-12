package com.urzaizcoding.iusteimanserver.domain.registration;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
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
@EqualsAndHashCode(exclude = { "course","parts","quitus" })
public class Folder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2330883772397945628L;

	private static String FORMAT = "yyyyMMdd-hhmm-";
	private static final DateTimeFormatter DATE_FORMATER;
	private static Integer counterSequence;

	static {
		DATE_FORMATER = DateTimeFormatter.ofPattern(FORMAT);
		counterSequence = 1;
	}

	@Getter(AccessLevel.NONE)
	private static final String FOLDER_SEQUENCE = "folder_sequence";

	@Id
	@SequenceGenerator(name = FOLDER_SEQUENCE, sequenceName = FOLDER_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = FOLDER_SEQUENCE)
	@Column(name = "folder_id")
	private Long id;

	@Column(nullable = false, length = 20)
	private String folderRegistrationNumber;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime creationDate;

	@Column(columnDefinition = "DATE")
	private LocalDate depositDate;

	@Column(nullable = false)
	private Boolean validated;

	@OneToMany(mappedBy = "folder", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<Part> parts;

	@Embedded
	private Form form;

	@OneToMany(mappedBy = "folder", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<Quitus> quitus;

	@OneToOne(mappedBy = "folder",fetch = FetchType.LAZY)
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	private Course course;

	public Folder() {
		super();
		this.parts = new HashSet<>();
		this.quitus = new HashSet<>();
	}

	public Part newPart() {
		Part part = new Part();
		parts.add(part);
		part.setFolder(this);
		return part;
	}

	public Quitus newQuitus() {
		Quitus nquitus = new Quitus();
		quitus.add(nquitus);
		nquitus.setFolder(this);
		return nquitus;
	}

	public static String generateNewNumber() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(DATE_FORMATER.format(LocalDateTime.now(AppConfigurer.appTimeZoneId())));

		synchronized (Folder.class) {
			stringBuilder.append(counterSequence++);
		}

		return stringBuilder.toString();
	}
}
