package com.urzaizcoding.iusteimanserver.domain.registration.manager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.urzaizcoding.iusteimanserver.domain.MaritalStatus;
import com.urzaizcoding.iusteimanserver.domain.Person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



@Entity(name = "Manager")
@Table(name = "manager")

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Manager extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7018811083939992943L;
	
	@Column(nullable = false, length = 10)
	private String internalId;
	
	@Column(nullable = false, length = 50)
	private String function;
	
	
	private MaritalStatus maritalStatus;
	
	

}
