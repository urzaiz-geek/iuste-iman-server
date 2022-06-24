package com.urzaizcoding.iusteimanserver.service;

import java.util.List;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.CourseRepository;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;

	public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
		super();
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
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

	@Override
	public Student subscribeStudent(Student studentEntity, Long courseId) throws ResourceNotFoundException {
		
		//get the concerned course
		
		Course concernedCourse =  courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The course identified by id : %d does not exist",courseId)));
		
		//create the folder
		
		return null;
	}

	@Override
	public Folder getRegistrationFolder(String folderRegistrationNumber) throws ResourceNotFoundException {
		return null;
	}

	@Override
	public Course updateCourse(Course courseEntity) throws ResourceNotFoundException {
		if (courseEntity.getId() == null) {
			throw new ResourceNotFoundException("The course identified by id : null does not exist");
		}
		courseRepository.findById(courseEntity.getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The course identified by id : %d does not exist", courseEntity.getId())));
		return courseRepository.save(courseEntity);
	}

}
