package com.urzaizcoding.iusteimanserver.dto;

import com.urzaizcoding.iusteimanserver.domain.registration.student.ParentAttribute;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ParentDTO {
	
	private final String names;
	private final String job;
	private final String contact;
	private final String regionOfOrigin;
	private final ParentAttribute attribute;
	private final String place;
	
	@Builder
	public ParentDTO(String names, String job, String contact, String regionOfOrigin, ParentAttribute attribute,
			String place) {
		super();
		this.names = names;
		this.job = job;
		this.contact = contact;
		this.regionOfOrigin = regionOfOrigin;
		this.attribute = attribute;
		this.place = place;
	}
	
	
}
