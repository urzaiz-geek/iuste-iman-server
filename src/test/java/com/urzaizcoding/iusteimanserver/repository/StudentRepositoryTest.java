package com.urzaizcoding.iusteimanserver.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.student.LanguageLevel;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Level;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;
import com.urzaizcoding.iusteimanserver.domain.registration.student.ParentAttribute;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;


@DataJpaTest
class StudentRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;
	
	@Autowired
	StudentRepository underTest;
	
	
	@Test
	void shouldAssertRepoIsNotNullAndEmptyAtStart() throws Exception {
		assertThat(underTest).isNotNull();
		assertThat(underTest.count()).isEqualTo(0);
	}
	
	@Test
	void shouldSaveANewStudent() throws Exception {
		//Given
		
		
		
		
		Student studentEntity = Student.builder()
				.firstName("Tresor")
				.lastName("Urzaiz")
				.birthDate(LocalDate.of(1997, 11, 13))
				.birthPlace("Bafoussam")
				.contact("690872959")
				.country("Cameroun")
				.countryOfGraduation("Cameroun")
				.entranceDiploma("Licence")
				.email("naruffygolen@gmail.com")
				.englishLevel(LanguageLevel.builder()
						.readLevel(Level.GOOD)
						.writeLevel(Level.GOOD)
						.comprehensionLevel(Level.GOOD)
						.speakLevel(Level.GOOD)
						.build())
				.frenchLevel(LanguageLevel.builder()
						.readLevel(Level.MEDIUM)
						.writeLevel(Level.MEDIUM)
						.comprehensionLevel(Level.MEDIUM)
						.speakLevel(Level.LITTLE)
						.build())
				.regionOfOrigin("OUEST")
				.schoolOfGraduation("IUSTE")
				.sex(Sex.MALE)
				.yearOfGraduation("2021")
				.build();
		
		
		Parent mother,father;
		
		mother = new Parent();
		mother.setNames("Tiofack Dongo");
		mother.setAttribute(ParentAttribute.MOTHER);
		mother.setJob("Enseignante");
		mother.setContact("699592077");
		mother.setPlace("Bafoussam");
		mother.setRegionOfOrigin("Ouest");
		father = new Parent();
		father.setNames("Gueboussi Tefossa");
		father.setAttribute(ParentAttribute.FATHER);
		father.setJob("Infirmier");
		father.setContact("670881413");
		father.setPlace("Bafoussam");
		father.setRegionOfOrigin("Ouest");
		
		studentEntity.addParent(father);
		studentEntity.addParent(mother);
		//When 
		
		studentEntity = underTest.save(studentEntity);
		
		//Then
		final Student retrived = testEntityManager.find(Student.class, studentEntity.getId());
		
		assertThat(studentEntity).isEqualTo(retrived);
		assertThat(retrived.getParents()).containsAll(Set.of(father,mother));
		
	}

}
