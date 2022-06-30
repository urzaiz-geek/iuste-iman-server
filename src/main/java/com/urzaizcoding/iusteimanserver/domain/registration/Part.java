package com.urzaizcoding.iusteimanserver.domain.registration;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Part")
@Table(name = "part", uniqueConstraints = {
		@UniqueConstraint(columnNames = "archivePath", name = "ArchivePathUniqueConstraint") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "part_spec_type", length = 3)

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Part implements Serializable{

	

	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -5661275494800687333L;

	@Getter(AccessLevel.NONE)
	private static final String PART_SEQUENCE = "part_sequence";

	@Id
	@SequenceGenerator(name = PART_SEQUENCE, sequenceName = PART_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PART_SEQUENCE)
	@Column(name = "part_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(length = 10)
	private String fileType;
	
	@Column(columnDefinition = "DATE")
	private LocalDate uploadDate;
	
	@Column
	private Long size;

	private String archivePath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Folder folder;

	public Part(Long id, String name, String description, String fileType, LocalDate uploadDate, Long size,
			String archivePath) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.fileType = fileType;
		this.uploadDate = uploadDate;
		this.size = size;
		this.archivePath = archivePath;
	}

	
	
}
