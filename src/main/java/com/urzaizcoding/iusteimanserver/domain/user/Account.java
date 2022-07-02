package com.urzaizcoding.iusteimanserver.domain.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "Account")
@Table(name = "account")

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Account implements Serializable {
	
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -6440248033893720606L;



	private static final String ACCOUNT_SEQUENCE = "account_sequence";
	
	
	
	@Id
	@SequenceGenerator(name = ACCOUNT_SEQUENCE,sequenceName = ACCOUNT_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = ACCOUNT_SEQUENCE)
	@Column(name = "account_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_account_creator_id")
	private Account creator;

	@Builder
	public Account(Long id, Account creator) {
		super();
		this.id = id;
		this.creator = creator;
	}
	
	
}
