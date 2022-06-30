package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderDTOLigth {
	private Long id;

	private String folderRegistrationNumber;

	private String creationDate;

	private LocalDate depositDate;
	
	private Boolean validated;
	
	private StudentDTOLigth student;
	
	private CourseDTOLigth course;
}
