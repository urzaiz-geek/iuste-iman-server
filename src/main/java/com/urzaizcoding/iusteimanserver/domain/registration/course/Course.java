package com.urzaizcoding.iusteimanserver.domain.registration.course;

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
import javax.persistence.Table;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Course")
@Table(name = "course")
@Data
@NoArgsConstructor
@ToString(exclude = {"folders"})
@EqualsAndHashCode(exclude = {"folders"})
public class Course {

	@Getter(AccessLevel.NONE)
	private static final String COURSE_SEQUENCE = "course_sequence";

	@Id
	@SequenceGenerator(sequenceName = COURSE_SEQUENCE, name = COURSE_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = COURSE_SEQUENCE)
	@Column(name = "course_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String faculty;

	@Column(nullable = false, length = 100)
	private String cycle;

	@Column(nullable = false, length = 80)
	private String speciality;

	@Column(nullable = false, length = 9)
	private String year;

	@Column(nullable = false)
	private Integer level;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<Folder> folders;

	@Builder
	protected Course(Long id, String faculty, String cycle, String speciality, String year, Integer level,
			Set<Folder> folders) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
		this.folders = folders;

		this.folders = folders == null ? new HashSet<>() : folders;
	}
		
	public void addFolder(Folder newFolder) {
		newFolder.setCourse(this);
		folders.add(newFolder);
	}

}
