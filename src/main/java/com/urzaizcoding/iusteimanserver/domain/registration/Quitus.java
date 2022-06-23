package com.urzaizcoding.iusteimanserver.domain.registration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Quitus")
@Table(name = "quitus")
@DiscriminatorValue("QUI")

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Quitus extends Part {
	
	private String cause;
	private Boolean status;
	private Integer amount;
	private String paiementPlace;
	
	@Builder
	public Quitus(Long id, String name, String description, String archivePath, String cause,
			Boolean status, Integer amount, String paiementPlace) {
		super(id, name, description, archivePath);
		this.cause = cause;
		this.status = status;
		this.amount = amount;
		this.paiementPlace = paiementPlace;
	}
	
	
}
