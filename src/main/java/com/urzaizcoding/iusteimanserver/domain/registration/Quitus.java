package com.urzaizcoding.iusteimanserver.domain.registration;

import java.io.InputStream;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Quitus")
@Table(name = "quitus")
@DiscriminatorValue("QUI")

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Quitus extends Part implements DocumentExportable{
	
	private String cause;
	private Boolean status;
	private Integer amount;
	private String paiementPlace;
	@Override
	public InputStream generatePDF() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
