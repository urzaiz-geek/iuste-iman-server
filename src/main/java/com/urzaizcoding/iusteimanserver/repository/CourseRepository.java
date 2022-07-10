package com.urzaizcoding.iusteimanserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	
	@Query("select f from Folder f where f.course.id = :courseId")
	Page<Folder> getFoldersOfCourse(Long courseId,Pageable page);

}
