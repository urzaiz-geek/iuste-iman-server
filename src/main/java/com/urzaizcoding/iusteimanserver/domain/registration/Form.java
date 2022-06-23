package com.urzaizcoding.iusteimanserver.domain.registration;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity(name = "Form")
@Table(name = "form")
@DiscriminatorValue("FOR")

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Form extends Part{
	
	@Column(nullable = false,columnDefinition = "DATE")
	private LocalDate generationDate;
	
	private Boolean isEditable;

	@Builder
	public Form(Long id, String name, String description, String archivePath, LocalDate generationDate,
			Boolean isEditable) {
		super(id, name, description, archivePath);
		this.generationDate = generationDate;
		this.isEditable = isEditable;
	}
	
	
}
