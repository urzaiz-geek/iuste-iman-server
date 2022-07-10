package com.urzaizcoding.iusteimanserver.domain.registration;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;




@Embeddable

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Form implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7562577602401391699L;

	@Column(columnDefinition = "DATE")
	private LocalDate generationDate;
	
	private Boolean isEditable = true;

	@Builder
	public Form(LocalDate generationDate,
			Boolean isEditable) {
		this.generationDate = generationDate;
		this.isEditable = isEditable;
	}
}
