package com.urzaizcoding.iusteimanserver.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.urzaizcoding.iusteimanserver.domain.registration.course.FeesType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FeesDTO {
	
	@NotNull @NotBlank
	private final String object;
	
	@NotNull @Positive
	private final Integer amount;
	
	@NotNull
	private final FeesType type;

	@Builder
	public FeesDTO(@NotNull @NotBlank String object, @NotNull @Positive Integer amount, @NotNull FeesType type) {
		super();
		this.object = object;
		this.amount = amount;
		this.type = type;
	}

	
	
	
	
	
}
