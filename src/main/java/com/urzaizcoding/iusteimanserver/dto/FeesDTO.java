package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.urzaizcoding.iusteimanserver.domain.registration.course.FeesType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeesDTO {
	
	@NotNull @NotBlank
	private String object;
	
	@NotNull @Positive
	private Integer amount;
	
	@NotNull
	private FeesType type;

	@Builder
	public FeesDTO(String object, Integer amount, FeesType type) {
		this.object = object;
		this.amount = amount;
		this.type = type;
	}
	
	
	
	
}
