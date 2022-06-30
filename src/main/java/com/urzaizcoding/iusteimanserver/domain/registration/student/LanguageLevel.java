package com.urzaizcoding.iusteimanserver.domain.registration.student;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageLevel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1443577419611557068L;
	
	@NotNull
	private Level readLevel;
	@NotNull
	private Level writeLevel;
	@NotNull
	private Level speakLevel;
	@NotNull
	private Level comprehensionLevel;
}
