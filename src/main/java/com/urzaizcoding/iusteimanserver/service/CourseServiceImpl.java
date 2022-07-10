package com.urzaizcoding.iusteimanserver.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.MailNotificationFailureException;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.CourseRepository;
import com.urzaizcoding.iusteimanserver.repository.ParentRepository;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final ParentRepository parentRepository;
	private final MailNotificationService mailNotificationService;

	public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
			ParentRepository parentRepository, MailNotificationService mailNotificationService) {
		super();
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.parentRepository = parentRepository;
		this.mailNotificationService = mailNotificationService;
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

	@Transactional
	@Override
	public Student subscribeStudent(Student studentEntity, Long courseId)
			throws ResourceNotFoundException, MailNotificationFailureException {

		// get the concerned course

		Course concernedCourse = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		Folder folder = concernedCourse.newFolder();
		studentEntity.setFolder(folder);

//TO-DO uncomment this line later
//		mailNotificationService.sendRegistrationEmail(studentEntity);

		return studentRepository.save(studentEntity);
	}

	@Transactional
	@Override
	public Student updateSubscription(Student studentEntity, Long courseId)
			throws ResourceNotFoundException, MailNotificationFailureException {
		// get the concerned course

		Course concernedCourse = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		// get the old student

		Optional<Student> oldStudent = studentRepository.findById(studentEntity.getId());
		Student refreshed = oldStudent.orElseThrow(() -> new ResourceNotFoundException(
				String.format("Resource identified by id : %d not found", studentEntity.getId())));

		refreshed.clearParents();
		refreshed.updateFromOther(studentEntity);

		// get the old course

		Course oldCourse = refreshed.getFolder().getCourse();
		if (!concernedCourse.equals(oldCourse)) {
			oldCourse.removeFolder(refreshed.getFolder());
			refreshed.setFolder(concernedCourse.newFolder());
			mailNotificationService.sendRegistrationEmail(refreshed);
		}

		parentRepository.deleteCleanParents();

		return refreshed;
	}

	@Override
	@Transactional
	public Page<Folder> getFoldersOfCourse(@NotNull @NotBlank Long courseId, Integer page, Integer size) {
		Course courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));
		
		PageRequest pageRequest = PageRequest.of(page == null? 0:page, size == null? 20:size);
		
		return courseRepository.getFoldersOfCourse(courseEntity.getId(),pageRequest);
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
