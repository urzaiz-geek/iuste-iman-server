package com.urzaizcoding.iusteimanserver.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CourseDTO {
	private Long id;
	@NotNull
	private String faculty;
	@NotNull
	private String cycle;
	@NotNull
	private String speciality;
	@NotNull
	private String year;
	@NotNull
	private Integer level;
	
	private Set<FeesDTO> fees;
	
	@Builder
	private CourseDTO(Long id, String faculty, String cycle, String speciality, String year, Integer level, Set<FeesDTO> fees) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
		this.fees = fees;
	}
	
	
}
