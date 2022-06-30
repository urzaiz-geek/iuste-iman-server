package com.urzaizcoding.iusteimanserver.dto;

import com.urzaizcoding.iusteimanserver.domain.registration.student.ParentAttribute;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ParentDTO {
	
	private String names;
	private String job;
	private String contact;
	private String regionOfOrigin;
	private ParentAttribute attribute;
	private String place;
	
	@Builder
	public ParentDTO(String names, String job, String contact, String regionOfOrigin,
			ParentAttribute attribute, String place) {
		super();
		this.names = names;
		this.job = job;
		this.contact = contact;
		this.regionOfOrigin = regionOfOrigin;
		this.attribute = attribute;
		this.place = place;
	}
}
