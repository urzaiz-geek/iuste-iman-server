package com.urzaizcoding.iusteimanserver.service;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.MailNotificationFailureException;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;

public interface CourseService {

	Course saveCourse(Course courseEntity);

	List<Course> getAllCourses();

	void deleteCourse(Long id) throws ResourceNotFoundException;

	Student subscribeStudent(Student studentEntity, Long courseId) throws ResourceNotFoundException, MailNotificationFailureException;

	Student updateSubscription(Student studentEntity, Long courseId) throws ResourceNotFoundException, MailNotificationFailureException;

	Set<Folder> getFoldersOfCourse(@NotNull @NotBlank Long courseId) throws ResourceNotFoundException;

	Course findSpecifiedCourse(Long id) throws ResourceNotFoundException;

	Course editCourseStatus(Boolean isOpen, @NotNull Long courseId) throws ResourceNotFoundException;
}
