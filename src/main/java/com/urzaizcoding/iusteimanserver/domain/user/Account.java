package com.urzaizcoding.iusteimanserver.domain.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Account")
@Table(name = "account")
public class Account implements Serializable {
	@Id
	private Long id;
}
