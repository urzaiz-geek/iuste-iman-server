package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;


@Getter
public class CourseDTO {
	private final Long id;
	@NotNull
	private final String faculty;
	@NotNull
	private final String cycle;
	@NotNull
	private final String speciality;
	@NotNull
	@Size(min = 9, max = 9)
	private final String year;
	@NotNull
	@Size(max = 5)
	private final Integer level;
	
	@Builder
	private CourseDTO(Long id, String faculty, String cycle, String speciality, String year, Integer level) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
	}
	
	
}
