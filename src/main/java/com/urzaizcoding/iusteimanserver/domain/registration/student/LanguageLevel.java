package com.urzaizcoding.iusteimanserver.domain.registration.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageLevel {
	private Level readLevel;
	private Level writeLevel;
	private Level speakLevel;
	private Level comprehensionLevel;
}
