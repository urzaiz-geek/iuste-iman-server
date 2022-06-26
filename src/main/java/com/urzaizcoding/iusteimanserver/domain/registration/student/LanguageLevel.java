package com.urzaizcoding.iusteimanserver.domain.registration.student;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageLevel {
	@NotNull
	private Level readLevel;
	@NotNull
	private Level writeLevel;
	@NotNull
	private Level speakLevel;
	@NotNull
	private Level comprehensionLevel;
}
