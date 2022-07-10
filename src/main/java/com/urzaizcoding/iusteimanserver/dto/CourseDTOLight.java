package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;


@Getter
public class CourseDTOLight {
	private final Long id;
	
	private final String faculty;
	
	private final String cycle;
	
	private final String speciality;
	
	private final String year;
	
	private final Integer level;
	@NotNull
	private final Boolean open;
	
	@Builder
	public CourseDTOLight(Long id, String faculty, String cycle, String speciality, String year, Integer level,
			@NotNull Boolean open) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
		this.open = open;
	}

	
	
	
}
