package com.urzaizcoding.iusteimanserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
