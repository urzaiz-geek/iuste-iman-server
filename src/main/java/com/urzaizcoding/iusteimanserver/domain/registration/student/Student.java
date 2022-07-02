package com.urzaizcoding.iusteimanserver.domain.registration.student;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.urzaizcoding.iusteimanserver.domain.Person;
import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.Folder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Student")
@Table(name = "student", uniqueConstraints = {
		@UniqueConstraint(columnNames = "registrationId", name = "RegistrationIdUniqueConstraint") })
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "folder", "parents" })
@ToString(callSuper = true, exclude = "folder")
public class Student extends Person {

	/**
	 * 
	 */
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 4995948429578480105L;

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

	@OneToMany(mappedBy = "student", cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Parent> parents;

	@OneToOne(fetch = FetchType.LAZY)
	private Folder folder;

	@Builder
	public Student(Long id, String firstName, String lastName, LocalDate birthDate, Sex sex, String birthPlace,
			String country, String contact, String email, String registrationId, String regionOfOrigin,
			String entranceDiploma, String yearOfGraduation, String schoolOfGraduation, String diplomaOption,
			String countryOfGraduation, LanguageLevel frenchLevel, LanguageLevel englishLevel, String photoPath,
			Set<Parent> parents) {
		super(id, firstName, lastName, birthDate, sex, birthPlace, country, contact, email);
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

		this.parents = parents == null ? new HashSet<>() : parents;
	}

	public void setFolder(Folder folder) {
		if (this.folder == null) {
			this.folder = folder;
			return;
		}
		this.folder = folder;
		folder.setStudent(this);
	}

	public void addParent(Parent newParent) {
		if (this.parents.size() < 3) {
			newParent.setStudent(this);
			parents.add(newParent);
		}
	}

	public void clearParents() {
		Iterator<Parent> iterator = this.parents.iterator();

		while (iterator.hasNext()) {
			Parent item = iterator.next();
			item.setStudent(null);
			iterator.remove();
		}
	}

	public void updateFromOther(Student studentEntity) {

		this.setBirthDate(studentEntity.getBirthDate());
		this.setBirthPlace(studentEntity.getBirthPlace());
		this.setContact(studentEntity.getContact());
		this.setCountry(studentEntity.getCountry());
		this.setCountryOfGraduation(studentEntity.getCountryOfGraduation());
		this.setDiplomaOption(studentEntity.getDiplomaOption());
		this.setEmail(studentEntity.getEmail());
		this.setEnglishLevel(studentEntity.getEnglishLevel());
		this.setEntranceDiploma(studentEntity.getEntranceDiploma());
		this.setFirstName(studentEntity.getFirstName());
		this.setFrenchLevel(studentEntity.getFrenchLevel());
		this.setLastName(studentEntity.getLastName());
		this.setPhotoPath(studentEntity.getPhotoPath());
		this.setRegionOfOrigin(studentEntity.getRegionOfOrigin());
		this.setSchoolOfGraduation(studentEntity.getSchoolOfGraduation());
		this.setSex(studentEntity.getSex());
		this.setYearOfGraduation(studentEntity.getYearOfGraduation());
		studentEntity.getParents().forEach(p -> this.addParent(p));

	}

	public void updateParents() {
		this.parents.stream().forEach(p -> p.setStudent(this));
	}

	public static String generateStudentRegistrationId() {
		return "dummy";
	}

}
