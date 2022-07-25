package com.urzaizcoding.iusteimanserver.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Role;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.exception.SubscriptionException;
import com.urzaizcoding.iusteimanserver.repository.CourseRepository;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final MailNotificationService mailNotificationService;
	private final AccountService accountService;

	public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
			MailNotificationService mailNotificationService, AccountService accountService) {
		super();
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.mailNotificationService = mailNotificationService;
		this.accountService = accountService;
	}

	@Override
	public Course saveCourse(Course courseEntity) {
		if (courseEntity.getId() != null) {
			courseRepository.findById(courseEntity.getId()).orElseThrow(() -> new ResourceNotFoundException(
					String.format("The course identified by id : %d does not exist", courseEntity.getId())));
		}

		courseEntity.updateInnerFees(); // as Fees will come with null course field
		return courseRepository.save(courseEntity);
	}

	@Override
	@Transactional
	public void deleteCourse(Long id) throws ResourceNotFoundException {
		Course toDelete = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", id)));

		courseRepository.delete(toDelete);
	}

	
	@Override
	public Student subscribeStudent(Student studentEntity, Long courseId) throws Exception {

		// get the concerned course

		Course concernedCourse = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		Folder folder = concernedCourse.newFolder();
		studentEntity.setFolder(folder);
		studentEntity.updateParents();

		// Account creation
		Account studentAccount = Account.builder().username(folder.getFolderRegistrationNumber())
				.creationDate(LocalDateTime.now(AppConfigurer.appTimeZoneId() ))
				.password(accountService.encryptPassword(folder.getFolderRegistrationNumber())).role(Role.PRE_STUDENT)
				.active(true).build();

		studentEntity.setAccount(studentAccount);

		try {
			studentEntity = studentRepository.save(studentEntity);
		}catch (Exception e) {
			throw new SubscriptionException("Subscription failed",e);
		}

		mailNotificationService.sendRegistrationEmail(studentEntity);

		return studentEntity;
	}

	@Transactional
	@Override
	public Student updateSubscription(Student studentEntity, Long courseId) throws Exception {
		// get the concerned course

		Course concernedCourse = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		// get the old student

		Optional<Student> oldStudent = studentRepository.findById(studentEntity.getId());
		Student refreshed = oldStudent.orElseThrow(() -> new ResourceNotFoundException(
				String.format("Resource identified by id : %d not found", studentEntity.getId())));

		refreshed.clearParents();
		refreshed.updateFromOther(studentEntity);
		studentEntity.getParents().forEach(p -> {
			refreshed.addParent(p);
		});

		// get the old course

		Course oldCourse = refreshed.getFolder().getCourse();
		if (!concernedCourse.equals(oldCourse)) {
			oldCourse.removeFolder(refreshed.getFolder());
			Folder newFolder = concernedCourse.newFolder();
			refreshed.setFolder(newFolder);
			Account account = refreshed.getAccount();
			System.out.println(account);
			account.setUsername(newFolder.getFolderRegistrationNumber());
			account.setPassword(accountService.encryptPassword(newFolder.getFolderRegistrationNumber()));

			mailNotificationService.sendRegistrationEmail(refreshed);
		}

//		parentRepository.deleteCleanParents();

		return refreshed;
	}

	@Override
	@Transactional
	public Page<Folder> getFoldersOfCourse(@NotNull @NotBlank Long courseId, Integer page, Integer size) {
		Course courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		PageRequest pageRequest = PageRequest.of(page == null ? 0 : page, size == null ? 20 : size);

		return courseRepository.getFoldersOfCourse(courseEntity.getId(), pageRequest);
	}

	@Override
	public Course findSpecifiedCourse(Long id) throws ResourceNotFoundException {
		return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", id)));
	}

	@Override
	public Course editCourseStatus(Boolean isOpen, @NotNull Long courseId) throws ResourceNotFoundException {

		Course courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		courseEntity.setOpen(isOpen);

		return courseRepository.save(courseEntity);
	}

	@Override
	public Page<Course> getAllCourses(Integer page, Integer size) {
		PageRequest pageRequest = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
		return courseRepository.findAll(pageRequest);
	}

}
