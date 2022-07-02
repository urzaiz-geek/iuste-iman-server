package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FolderDTOLigth {
	private final Long id;

	private final String folderRegistrationNumber;

	private final String creationDate;

	private final LocalDate depositDate;
	
	private final Boolean validated;
	
	private final StudentDTOLigth student;
	
	private final CourseDTOLigth course;

	@Builder
	public FolderDTOLigth(Long id, String folderRegistrationNumber, String creationDate, LocalDate depositDate,
			Boolean validated, StudentDTOLigth student, CourseDTOLigth course) {
		super();
		this.id = id;
		this.folderRegistrationNumber = folderRegistrationNumber;
		this.creationDate = creationDate;
		this.depositDate = depositDate;
		this.validated = validated;
		this.student = student;
		this.course = course;
	}
	
	
	
}
