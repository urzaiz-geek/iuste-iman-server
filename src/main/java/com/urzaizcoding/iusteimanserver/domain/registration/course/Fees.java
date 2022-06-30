package com.urzaizcoding.iusteimanserver.domain.registration.course;

import java.io.Serializable;

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

@Entity(name = "Fees")
@Table(name = "fees")

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"course"})
@ToString
public class Fees implements Serializable {
	
	/**
	 * 
	 */
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 457695979536184441L;

	@Getter(AccessLevel.NONE)
	private static final String FEES_SEQUENCE = "fees_sequence";
	
	@Id
	@SequenceGenerator(name = FEES_SEQUENCE, sequenceName = FEES_SEQUENCE, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = FEES_SEQUENCE)
	@Column(name = "fees_id")
	private Long Id;
	
	@Column(length = 100, nullable = false)
	private String object;
	
	@Column(nullable = false)
	private Integer amount;
	
	@Column(length = 2)
	private FeesType type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_course_id",referencedColumnName = "course_id")
	private Course course;

	@Builder
	public Fees(Long id, String object, Integer amount, FeesType type, Course course) {
		super();
		Id = id;
		this.object = object;
		this.amount = amount;
		this.type = type;
		this.course = course;
	}
	
}
