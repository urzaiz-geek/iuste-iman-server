package com.urzaizcoding.iusteimanserver.domain.registration.course;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
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
import com.urzaizcoding.iusteimanserver.domain.registration.Form;
import com.urzaizcoding.iusteimanserver.domain.registration.Quitus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity(name = "Course")
@Table(name = "course")
@Data
@ToString(exclude = { "folders","fees" })
@EqualsAndHashCode(exclude = { "folders","fees" })
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1991625014647089271L;

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
	
	private Boolean isOpen = true;

	@OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, orphanRemoval = true,fetch = FetchType.LAZY)
	private Set<Folder> folders;

	@OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, orphanRemoval = true,fetch = FetchType.LAZY)
	private Set<Fees> fees;

	@Builder
	public Course(Long id, String faculty, String cycle, String speciality, String year, Integer level,
			Set<Fees> fees, Set<Folder> folders) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
		this.folders = folders;

		this.fees = fees == null ? new HashSet<>() : fees;
		this.folders = new HashSet<>();
	}
	
	public Course() {
		this.fees = new HashSet<>();
		this.folders = new HashSet<>();
	}

	public Folder newFolder() {

		Folder folder = new Folder();

		// Generate Quitus for default fees

		fees.stream().filter(f -> f.getType() == FeesType.DEFAULT).forEach(f -> {
			Quitus q = folder.newQuitus();
			q.setAmount(f.getAmount());
			q.setObject(q.getObject());
			q.setPaiementPlace("Etablissement");
		});

		folder.setCreationDate(LocalDateTime.now());
		folder.setFolderRegistrationNumber(Folder.generateNewNumber());
		folder.setValidated(false);
		folder.setForm(Form.builder().generationDate(LocalDate.now()).isEditable(true).build());

		folders.add(folder);
		folder.setCourse(this);

		return folder;
	}

	public void removeFolder(Folder folder) {
		if (folders.contains(folder)) {
			folders.remove(folder);
			folder.setCourse(null);
		}
	}

	public void clearFees() {
		Iterator<Fees> iterator = this.fees.iterator();
		
		while(iterator.hasNext()){
			Fees item = iterator.next();
			iterator.remove();
			item.setCourse(null);
		}
	}
	
	public void addFees(Fees fees) {
		this.fees.add(fees);
		fees.setCourse(this);
	}

	public void updateFromOther(Course courseEntity) {
		this.setCycle(courseEntity.getCycle());
		this.setFaculty(courseEntity.getFaculty());
		this.setLevel(courseEntity.getLevel());
		this.setSpeciality(courseEntity.getSpeciality());
		this.setYear(courseEntity.getYear());
		courseEntity.getFees().stream().forEach(f -> this.addFees(f));
		
	}

	public void updateInnerFees() {
		this.fees.stream().forEach(f -> f.setCourse(this));
	}

}
