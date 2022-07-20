package com.urzaizcoding.iusteimanserver.domain.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	public static final Long PASSWORD_VALIDITY = 60L; //in days
	public static final Long FIRST_PASSWORD_RESET_PERIOD = 10L; //in minits 
	
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -6440248033893720606L;



	private static final String ACCOUNT_SEQUENCE = "account_sequence";
	
	
	
	@Id
	@SequenceGenerator(name = ACCOUNT_SEQUENCE,sequenceName = ACCOUNT_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = ACCOUNT_SEQUENCE)
	@Column(name = "account_id")
	private Long id;
	
	@Column(unique = true, length = 25, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime creationDate;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime lastConnectionDate;
	
	private boolean active;
	
	@Column(length = 1)
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_account_creator_id")
	private Account creator;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Notification> notifications; 

	@Builder
	public Account(Long id, String username, String password, LocalDateTime creationDate, LocalDateTime lastConnexionDate,
			boolean active, Role role, Account creator, Set<Notification> notifications) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.creationDate = creationDate;
		this.lastConnectionDate = lastConnexionDate;
		this.active = active;
		this.role = role;
		this.creator = creator;
		this.notifications = notifications;
	}
	
	
	public void addNotification(Notification notification) {
		this.notifications.add(notification);
		notification.setAccount(this);
	}
	
	public void removeNotification(Notification notification) {
		this.notifications.remove(notification);
		notification.setAccount(null);
	}

	
}
