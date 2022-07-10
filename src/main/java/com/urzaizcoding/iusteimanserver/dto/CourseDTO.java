package com.urzaizcoding.iusteimanserver.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

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
	private final String year;
	@NotNull
	private final Integer level;
	
	@NotNull
	private final Boolean open;
	
	private Set<FeesDTO> fees;

	@Builder
	public CourseDTO(Long id, @NotNull String faculty, @NotNull String cycle, @NotNull String speciality,
			@NotNull String year, @NotNull Integer level, @NotNull Boolean open, Set<FeesDTO> fees) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
		this.open = open;
		this.fees = fees;
	}
	
	
	
	
}
