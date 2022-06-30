package com.urzaizcoding.iusteimanserver.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CourseDTOLigth {
	private Long id;
	
	private String faculty;
	
	private String cycle;
	
	private String speciality;
	
	private String year;
	
	private Integer level;

	@Builder
	public CourseDTOLigth(Long id, String faculty, String cycle, String speciality, String year, Integer level) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.cycle = cycle;
		this.speciality = speciality;
		this.year = year;
		this.level = level;
	}
	
	
}
