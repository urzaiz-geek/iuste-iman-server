package com.urzaizcoding.iusteimanserver.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.CourseRepository;
import com.urzaizcoding.iusteimanserver.repository.ParentRepository;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final ParentRepository parentRepository;

	public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
			ParentRepository parentRepository) {
		super();
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.parentRepository = parentRepository;
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
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public void deleteCourse(Long id) throws ResourceNotFoundException {
		Course toDelete = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", id)));
		courseRepository.delete(toDelete);
	}

	@Transactional
	@Override
	public Student subscribeStudent(Student studentEntity, Long courseId) throws ResourceNotFoundException {

		// get the concerned course

		Course concernedCourse = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		Folder folder = concernedCourse.newFolder();
		studentEntity.setFolder(folder);

		return studentRepository.save(studentEntity);
	}

	@Transactional
	@Override
	public Student updateSubscription(Student studentEntity, Long courseId) {
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
		}

		parentRepository.deleteCleanParents();

		return refreshed;
	}

	@Override
	@Transactional
	public Set<Folder> getFoldersOfCourse(@NotNull @NotBlank Long courseId) {
		Course courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));
		return courseEntity.getFolders();
	}

}
