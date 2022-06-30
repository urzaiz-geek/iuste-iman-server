package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;
import java.util.Set;

import com.urzaizcoding.iusteimanserver.domain.registration.Form;
import com.urzaizcoding.iusteimanserver.domain.registration.Part;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class FolderDTO {
	private Long id;

	private String folderRegistrationNumber;

	private String creationDate;

	private LocalDate depositDate;
	
	private Boolean validated;

	private Set<Part> parts;

	private Form form;

	private StudentDTO student;

	private CourseDTO course;

	@Builder
	public FolderDTO(Long id, String folderRegistrationNumber, String creationDate, LocalDate depositDate,
			Boolean validated, Set<Part> parts, Form form, StudentDTO student, CourseDTO course) {
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
