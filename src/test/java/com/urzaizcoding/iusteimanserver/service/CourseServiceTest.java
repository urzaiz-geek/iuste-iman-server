package com.urzaizcoding.iusteimanserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Fees;
import com.urzaizcoding.iusteimanserver.domain.registration.course.FeesType;
import com.urzaizcoding.iusteimanserver.domain.registration.student.LanguageLevel;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Level;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;
import com.urzaizcoding.iusteimanserver.domain.registration.student.ParentAttribute;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.repository.CourseRepository;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
	
	@Mock
	private  CourseRepository courseRepository;
	
	@Mock
	private  StudentRepository studentRepository;
	
	@Mock
	private MailNotificationService mailNotificationService;
	
	@Mock
	private AccountService accountService;
	
	private CourseService underTest;
	
	@BeforeEach
	void setup() throws Exception {
		underTest = new CourseServiceImpl(courseRepository, studentRepository, mailNotificationService, accountService);
	}
	
	@Test
	void shouldTestIfWhenSavingCourseRepoIsCalled() throws Exception {
		//Given
		
		final Course courseEntity = Course.builder()
				.cycle("Licence")
				.level(3)
				.faculty("Genie Informatique")
				.speciality("Genie Logiciel")
				.fees(Set.of(
						Fees.builder()
						.amount(150000)
						.object("Pension")
						.type(FeesType.DEFAULT)
						.build()
				))
				.build();
		
		ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
		
		when(courseRepository.save(courseCaptor.capture())).thenReturn(courseEntity);
		
		//When
		
		final Course course = underTest.saveCourse(courseEntity);
		
		//Then
		verify(courseRepository,times(1)).save(courseCaptor.getValue());
		assertThat(courseEntity).isEqualTo(course);
	}
	
	@Test
	void shouldTestIfWhenGetAllCourseIsCalledfindAllIsCalledOnRepo() throws Exception {
		//Given
		final Course courseEntity = Course.builder()
				.cycle("Licence")
				.level(3)
				.faculty("Genie Informatique")
				.speciality("Genie Logiciel")
				.fees(Set.of(
						Fees.builder()
						.amount(150000)
						.object("Pension")
						.type(FeesType.DEFAULT)
						.build()
				))
				.build();
		
		when(courseRepository.findAll()).thenReturn(List.of(courseEntity));
		
		//When
		
		Page<Course> fetched = underTest.getAllCourses(null, null);
		
		//Then
		
		verify(courseRepository,times(1)).findAll();
		assertThat(fetched.toList().contains(courseEntity));
	}
	
	@Test
	void shouldTestIfWhenDeletingACourseDeleteFromRepoIsCalled() throws Exception {
		//Given
		
		ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
		
		when(courseRepository.findById(idCaptor.capture())).thenReturn(Optional.of(new Course()));
		
		//When
		
		underTest.deleteCourse(1L);
		
		//Then
		
		verify(courseRepository,times(1)).delete(any(Course.class));
		verify(courseRepository,times(1)).findById(idCaptor.getValue());
	}
	
	@Test
	void shouldTestIfWhenSubscribingAStudentCorrectReposAreCalled() throws Exception {
		//Given
		
		ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
		
		Parent father,mother;
		
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
		Student studentEntity = Student.builder().firstName("Tresor").lastName("Urzaiz")
				.birthDate(LocalDate.of(1997, 11, 13)).birthPlace("Bafoussam").contact("690872959").country("Cameroun")
				.countryOfGraduation("Cameroun").entranceDiploma("Licence").diplomaOption("GL")
				.email("naruffygolen@gmail.com")
				.englishLevel(LanguageLevel.builder().readLevel(Level.GOOD).writeLevel(Level.GOOD)
						.comprehensionLevel(Level.GOOD).speakLevel(Level.GOOD).build())
				.frenchLevel(LanguageLevel.builder().readLevel(Level.MEDIUM).writeLevel(Level.MEDIUM)
						.comprehensionLevel(Level.MEDIUM).speakLevel(Level.LITTLE).build())
				.regionOfOrigin("OUEST").schoolOfGraduation("IUSTE").sex(Sex.MALE).yearOfGraduation("2021").build();

		studentEntity.addParent(mother);
		studentEntity.addParent(father);
		
		when(studentRepository.save(studentCaptor.capture())).thenReturn(studentEntity);
		when(courseRepository.findById(idCaptor.capture())).thenReturn(Optional.of(new Course()));
		
		//When
		final Student saved = underTest.subscribeStudent(studentEntity, 1L);
		
		//Then
		verify(courseRepository,times(1)).findById(idCaptor.getValue());
		verify(studentRepository,times(1)).save(studentCaptor.getValue());
		assertThat(saved).isEqualTo(studentEntity);
	}
	
	@Test
	void shouldTestIfWhenUpdatingSubscriptionReposCalled() throws Exception {
		//Given
		
		//When
		
		//Then
	}

}
