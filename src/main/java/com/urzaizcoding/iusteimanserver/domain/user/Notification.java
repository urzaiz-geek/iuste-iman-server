package com.urzaizcoding.iusteimanserver.domain.user;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity(name = "Notification")
@Table(name = "notification")

@Data
@EqualsAndHashCode
@ToString
public class Notification {

	@Getter(value = AccessLevel.NONE)
	private static final String NOTIFICATION_SEQUENCE = "notification_sequence";

	@Id
	@SequenceGenerator(name = NOTIFICATION_SEQUENCE, sequenceName = NOTIFICATION_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NOTIFICATION_SEQUENCE)
	private Long id;
	
	@Column(length = 100)
	private String subject;
	
	@Column(length = 400, columnDefinition = "TEXT")
	private String content;
	
	@Column(columnDefinition = "DATE")
	private LocalDate issuedDate;
	
	private Boolean read;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@Builder
	public Notification(Long id, String subject, String content, LocalDate issuedDate, Boolean read) {
		super();
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.issuedDate = issuedDate;
		this.read = read;
	}
	
}
