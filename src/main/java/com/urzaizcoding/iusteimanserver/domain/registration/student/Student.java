package com.urzaizcoding.iusteimanserver.domain.registration.student;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
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

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE,
			CascadeType.MERGE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Parent> parents;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE })
	private Folder folder;

	@Builder
	public Student(Long id, String firstName, String lastName, LocalDate birthDate, Sex sex, String birthPlace,
			String country, String contact, String email, String registrationId, String regionOfOrigin,
			String entranceDiploma, String yearOfGraduation, String schoolOfGraduation, String diplomaOption,
			String countryOfGraduation, LanguageLevel frenchLevel, LanguageLevel englishLevel, String photoPath) {
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

		parents = new HashSet<>();
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
		folder.setStudent(this);
	}

	public void addParent(Parent newParent) {
		if (this.parents.size() < 3) {
			Optional<Parent> p = this.parents.stream().filter(tp -> tp.getContact().equals(newParent.getContact()))
					.findFirst();
			if (p.isPresent()) {
				p.get().setAttribute(newParent.getAttribute());
				p.get().setContact(newParent.getContact());
				p.get().setJob(newParent.getJob());
				p.get().setNames(newParent.getNames());
				p.get().setPlace(newParent.getPlace());
				p.get().setRegionOfOrigin(newParent.getRegionOfOrigin());
			} else {
				parents.add(newParent);
				newParent.setStudent(this);
			}
		} else {
			Parent p = this.parents.stream().filter(tp -> tp.getContact().equals(newParent.getContact())).findFirst()
					.get();

			if (p == null) {
				p = this.parents.stream().filter(tp -> tp.getAttribute().equals(newParent.getAttribute())).findFirst()
						.get();

				p.setContact(newParent.getContact());
				p.setJob(newParent.getJob());
				p.setNames(newParent.getNames());
				p.setPlace(newParent.getPlace());
				p.setRegionOfOrigin(newParent.getRegionOfOrigin());
			}
		}
	}

	public void clearParents() {
		this.parents.clear();
	}

}
