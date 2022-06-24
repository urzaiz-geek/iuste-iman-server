package com.urzaizcoding.iusteimanserver.service;

import java.util.List;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;

public interface CourseService {

	Course addCourse(Course courseEntity);

	List<Course> getAllCourses();

	Course updateCourse(Course courseEntity) throws ResourceNotFoundException;

	void deleteCourse(Long id) throws ResourceNotFoundException;

	Student subscribeStudent(Student studentEntity, Long id) throws ResourceNotFoundException;

	Folder getRegistrationFolder(String folderRegistrationNumber) throws ResourceNotFoundException;
}
