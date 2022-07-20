package com.urzaizcoding.iusteimanserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urzaizcoding.iusteimanserver.domain.user.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findAccountByUsername(String username);
}
