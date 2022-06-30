package com.urzaizcoding.iusteimanserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urzaizcoding.iusteimanserver.domain.Person;

public interface PersonRepository<T extends Person> extends JpaRepository<T, Long> {
	
}
