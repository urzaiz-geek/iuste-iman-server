package com.urzaizcoding.iusteimanserver.domain.registration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity(name = "Folder")
@Table(name = "folder", uniqueConstraints = {
		@UniqueConstraint(columnNames = "folderRegistrationNumber", name = "FolderRegistrationNumberUniqueConstraint") })

@Data
@ToString
@EqualsAndHashCode
public class Folder {

	@Getter(AccessLevel.NONE)
	private static final String FOLDER_SEQUENCE = "folder_sequence";

	@Id
	@SequenceGenerator(name = FOLDER_SEQUENCE, sequenceName = FOLDER_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = FOLDER_SEQUENCE)
	@Column(name = "folder_id")
	private Long id;

	@Column(nullable = false, length = 15)
	private String folderRegistrationNumber;

	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate creationDate;

	@Column(columnDefinition = "DATE")
	private LocalDate depositDate;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<Part> parts;
	
	@OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.EAGER)
	private Form form;

	@Builder
	private Folder(Long id, String folderRegistrationNumber, LocalDate creationDate, LocalDate depositDate,
			Set<Part> parts, Form form) {
		super();
		this.id = id;
		this.folderRegistrationNumber = folderRegistrationNumber;
		this.creationDate = creationDate;
		this.depositDate = depositDate;
		this.parts = parts == null? new HashSet<>():parts;
		this.form = form;
	}
	
	
	public void addPart(Part part) {
		if(part != null) {
			parts.add(part);
		}
	}

}
