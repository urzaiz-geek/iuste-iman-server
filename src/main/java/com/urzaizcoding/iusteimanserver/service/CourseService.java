package com.urzaizcoding.iusteimanserver.service;

import java.util.List;

import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;

public interface CourseService {

	Course addCourse(Course courseEntity);

	List<Course> getAllCourses();

	Course updateCourse(Long id, Course courseEntity) throws ResourceNotFoundException;

	void deleteCourse(Long id) throws ResourceNotFoundException;
}
