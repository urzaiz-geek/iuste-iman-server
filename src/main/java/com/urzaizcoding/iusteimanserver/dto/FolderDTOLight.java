package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FolderDTOLight {
	private final Long id;

	private final String folderRegistrationNumber;

	private final String creationDate;

	private final LocalDate depositDate;
	
	private final Boolean validated;
	
	private final StudentDTOLight student;
	
	private final CourseDTOLight course;

	@Builder
	public FolderDTOLight(Long id, String folderRegistrationNumber, String creationDate, LocalDate depositDate,
			Boolean validated, StudentDTOLight student, CourseDTOLight course) {
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
