package com.urzaizcoding.iusteimanserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long>{
	
	@Modifying
	@Query("DELETE FROM Parent p WHERE p.student.id = null")
	void deleteCleanParents();
	
}
