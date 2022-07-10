package com.urzaizcoding.iusteimanserver.domain.registration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Quitus")
@Table(name = "quitus")

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
public class Quitus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6647152144067443404L;

	private static final String QUITUS_SEQUENCE = "quitus_sequence";

	@Id
	@SequenceGenerator(name = QUITUS_SEQUENCE, sequenceName = QUITUS_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = QUITUS_SEQUENCE)
	@Column(name = "quitus_id")
	private Long id;
	
	@Column(length = 150)
	private String object;
	private Boolean status;
	private Integer amount;
	@Column(length = 150)
	private String paiementPlace;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Folder folder;
	
	
}
