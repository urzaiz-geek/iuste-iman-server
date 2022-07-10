/**
 * 
 */
package com.urzaizcoding.iusteimanserver.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

/**
 * @author URZAIZ
 *
 */

@Getter
public class FolderDTOEmbedded {
	private final Long id;

	private final String folderRegistrationNumber;

	private final String creationDate;

	private final LocalDate depositDate;
	
	private final Boolean validated;
	
	private final StudentDTOLight student;

	@Builder
	public FolderDTOEmbedded(Long id, String folderRegistrationNumber, String creationDate, LocalDate depositDate,
			Boolean validated, StudentDTOLight student) {
		super();
		this.id = id;
		this.folderRegistrationNumber = folderRegistrationNumber;
		this.creationDate = creationDate;
		this.depositDate = depositDate;
		this.validated = validated;
		this.student = student;
	}
	
	
}
