package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PartDTO {
	private final Long id;

	private final String name;

	private final String description;

	@NotNull
	private final String fileType;

	private final String uploadDate;

	private final Long size;

	private final String archivePath;
	
	private final Long folderId;

	
	@Builder
	public PartDTO(Long id, String name, String description, String fileType, String uploadDate, Long size,
			String archivePath, Long folderId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.fileType = fileType;
		this.uploadDate = uploadDate;
		this.size = size;
		this.archivePath = archivePath;
		this.folderId = folderId;
	}
	
	
}
