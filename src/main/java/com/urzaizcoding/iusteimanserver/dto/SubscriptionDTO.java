package com.urzaizcoding.iusteimanserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubscriptionDTO {
	private final StudentDTO student;
	private final CourseDTO course;
	
	@Builder
	private SubscriptionDTO(StudentDTO student, CourseDTO course) {
		super();
		this.student = student;
		this.course = course;
	}
	
	
}
