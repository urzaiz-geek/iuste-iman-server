package com.urzaizcoding.iusteimanserver.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.CourseRepository;
import com.urzaizcoding.iusteimanserver.repository.FolderRepository;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final FolderRepository folderRepository;

	public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
			FolderRepository folderRepository) {
		super();
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.folderRepository = folderRepository;
	}

	@Override
	public Course addCourse(Course courseEntity) {
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

		// create the folder

		Folder folder = Folder.newFolder();
		studentEntity.setFolder(folder);

		System.out.println(folder);

		Student student = studentRepository.save(studentEntity);
		concernedCourse.addFolder(student.getFolder());

		System.out.println(concernedCourse.getFolders());

		courseRepository.save(concernedCourse);

		return student;
	}

	@Override
	public Folder getRegistrationFolder(String folderRegistrationNumber) throws ResourceNotFoundException {
		Folder folder = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(String
						.format("The Folder identified by identifier : %s does not exist", folderRegistrationNumber)));
		return folder;
	}

	@Override
	public Course updateCourse(Course courseEntity) throws ResourceNotFoundException {
		if (courseEntity.getId() == null) {
			throw new ResourceNotFoundException("The course identified by id : null does not exist");
		}
		courseRepository.findById(courseEntity.getId()).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseEntity.getId())));
		return courseRepository.save(courseEntity);
	}

	@Transactional
	@Override
	public Student updateStudentRegistration(Student studentEntity, Long courseId) {
		// get the concerned course

		Course concernedCourse = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist", courseId)));

		// get the old student

		Optional<Student> oldStudent = studentRepository.findById(studentEntity.getId());

		if (!oldStudent.isPresent()) {
			studentEntity.setId(null);
			return subscribeStudent(studentEntity, courseId);
		}

		Student refreshed = oldStudent.get();
		
		refreshed.setBirthDate(studentEntity.getBirthDate());
		refreshed.setBirthPlace(studentEntity.getBirthPlace());
		refreshed.setContact(studentEntity.getContact());
		refreshed.setCountry(studentEntity.getCountry());
		refreshed.setCountryOfGraduation(studentEntity.getCountryOfGraduation());
		refreshed.setDiplomaOption(studentEntity.getDiplomaOption());
		refreshed.setEmail(studentEntity.getEmail());
		refreshed.setEnglishLevel(studentEntity.getEnglishLevel());
		refreshed.setEntranceDiploma(studentEntity.getEntranceDiploma());
		refreshed.setFirstName(studentEntity.getFirstName());
		refreshed.setFrenchLevel(studentEntity.getFrenchLevel());
		refreshed.setLastName(studentEntity.getLastName());
		refreshed.setPhotoPath(studentEntity.getPhotoPath());
		refreshed.setRegionOfOrigin(studentEntity.getRegionOfOrigin());
		refreshed.setSchoolOfGraduation(studentEntity.getSchoolOfGraduation());
		refreshed.setSex(studentEntity.getSex());
		refreshed.setYearOfGraduation(studentEntity.getYearOfGraduation());
		final Student tmp = refreshed;
		studentEntity.getParents().forEach(p -> tmp.addParent(p));

		// get the old course

		Course oldCourse = refreshed.getFolder().getCourse();
		System.out.println(refreshed.getParents());
		System.out.println(refreshed.getFolder());
		if (!concernedCourse.equals(oldCourse)) {
			// we delete his folder in the last course first
			oldCourse.getFolders().remove(refreshed.getFolder());
			courseRepository.save(oldCourse);

			Folder folder = Folder.newFolder();
			concernedCourse.addFolder(folder);
			refreshed.setFolder(folder);
		}
		
		refreshed = studentRepository.save(refreshed);
		courseRepository.save(concernedCourse);

		return refreshed;
	}

}
