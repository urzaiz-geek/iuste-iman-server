package com.urzaizcoding.iusteimanserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegistrationDTO {
	private final StudentDTO student;
	private final CourseDTO course;
	
	@Builder
	private RegistrationDTO(StudentDTO student, CourseDTO course) {
		super();
		this.student = student;
		this.course = course;
	}
	
	
}
