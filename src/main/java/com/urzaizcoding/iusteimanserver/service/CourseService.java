package com.urzaizcoding.iusteimanserver.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.MailNotificationFailureException;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;

public interface CourseService {

	Course saveCourse(Course courseEntity);

	Page<Course> getAllCourses(Integer page, Integer size);

	void deleteCourse(Long id) throws ResourceNotFoundException;

	Student subscribeStudent(Student studentEntity, Long courseId) throws ResourceNotFoundException, MailNotificationFailureException;

	Student updateSubscription(Student studentEntity, Long courseId) throws ResourceNotFoundException, MailNotificationFailureException;

	Page<Folder> getFoldersOfCourse(@NotNull @NotBlank Long courseId, Integer page, Integer size) throws ResourceNotFoundException;

	Course findSpecifiedCourse(Long id) throws ResourceNotFoundException;

	Course editCourseStatus(Boolean isOpen, @NotNull Long courseId) throws ResourceNotFoundException;
}
