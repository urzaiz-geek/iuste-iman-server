package com.urzaizcoding.iusteimanserver.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.urzaizcoding.iusteimanserver.configuration.security.Token;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Notification;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;

public interface AccountService {

	Optional<Account> findUserByUsername(String username);
	
	Account saveAccount(Account account, Long ownerId) throws Exception;
	
	void deleteAccountById(Long accountId) throws ResourceNotFoundException;

	Page<Account> getAllAccounts(Integer page, Integer size);

	String encryptPassword(String folderRegistrationNumber);

	Account getSpecificAccount(@NotNull Long id);

	Account updateAccountStatus(@NotNull Long id,boolean status);

	Notification getSpecificNotification(Long id, Long notifId);

	Page<Notification> getAccountNotification(Long id, Integer page, Integer size);

	Token refreshToken(HttpServletRequest request) throws Exception;

	static String randPassword(int length) {
		return "bonjour";
	}

}
