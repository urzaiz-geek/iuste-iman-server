package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;
import java.util.Set;

import com.urzaizcoding.iusteimanserver.domain.registration.Form;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FolderDTO {
	private final Long id;

	private final  String folderRegistrationNumber;

	private final String creationDate;

	private final LocalDate depositDate;
	
	private final Boolean validated;

	private final Set<PartDTO> parts;

	private final Form form;

	private final StudentDTO student;

	private final CourseDTO course;

	@Builder
	public FolderDTO(Long id, String folderRegistrationNumber, String creationDate, LocalDate depositDate,
			Boolean validated, Set<PartDTO> parts, Form form, StudentDTO student, CourseDTO course) {
		super();
		this.id = id;
		this.folderRegistrationNumber = folderRegistrationNumber;
		this.creationDate = creationDate;
		this.depositDate = depositDate;
		this.validated = validated;
		this.parts = parts;
		this.form = form;
		this.student = student;
		this.course = course;
	}

	
	
	
}
