package com.urzaizcoding.iusteimanserver.domain.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Account")
@Table(name = "account")
public class Account {
	@Id
	private Long id;
}
