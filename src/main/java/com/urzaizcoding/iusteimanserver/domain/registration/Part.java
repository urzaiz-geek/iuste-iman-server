package com.urzaizcoding.iusteimanserver.domain.registration;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Part implements Serializable {

	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -5661275494800687333L;

	private static String NAME_FORMAT = "%s-%s.%s";

	private static String DATE_TIME_FORMAT = "yyyy-MM-dd-hh-mm-ss";

	@Getter(AccessLevel.NONE)
	private static final String PART_SEQUENCE = "part_sequence";

	public static final String BASE_PART_FORMAT = "/courses/%d/folders/%s";

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
	private LocalDateTime uploadDate;

	private Long size;

	private String archivePath;

	@ManyToOne(fetch = FetchType.LAZY)
	private Folder folder;

	public Part(Long id, String name, String description, String fileType, LocalDateTime uploadDate, Long size,
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

	/***
	 * This method role is to ensure that we will not have two files with the same
	 * name on the storage It takes nothing as input but return the former name of
	 * the path plus the actual timestamp of the moment the file is received
	 * 
	 * 
	 * @throws {@link IllegalStateExcetion}
	 */
	public void updatePartName(String originalFileName) {
		if (name == null) {
			// set it with the oriinal file name
			this.name = originalFileName;
		}

		if (!this.name.contains("\\.")) {
			// we git it the same extension as the originalFileName
			this.name = String.format("%s.%s",name,
					originalFileName.substring(originalFileName.indexOf("."), originalFileName.length()));
		}

		String strictName = name.substring(0, name.indexOf("."));
		String extension = name.substring(name.indexOf("."), name.length());
		DateTimeFormatter formater = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

		this.name = String.format(NAME_FORMAT, strictName, formater.format(uploadDate), extension);
	}

}
