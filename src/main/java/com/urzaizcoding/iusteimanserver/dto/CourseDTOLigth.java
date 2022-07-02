package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;


@Getter
public class CourseDTOLigth {
	private final Long id;
	
	private final String faculty;
	
	private final String cycle;
	
	private final String speciality;
	
	private final String year;
	
	private final Integer level;
	@NotNull
	private final Boolean isOpen;
	
	@Builder
	public CourseDTOLigth(Long id, String faculty, String cycle, String speciality, String year, Integer level,
			@NotNull Boolean isOpen) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
		this.isOpen = isOpen;
	}

	
	
	
}
