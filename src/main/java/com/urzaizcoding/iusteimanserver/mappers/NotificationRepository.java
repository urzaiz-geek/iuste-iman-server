package com.urzaizcoding.iusteimanserver.mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.urzaizcoding.iusteimanserver.domain.user.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("select n from Notification n where n.account.id = :id") 
	Page<Notification> findAllByAccount(Long id, Pageable pageable);

}
