package com.urzaizcoding.iusteimanserver.configuration.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Role;
import com.urzaizcoding.iusteimanserver.repository.AccountRepository;

@Component("accountSecurity")
public class AccountSecurity {

	private final AccountRepository accountRepository;

	public AccountSecurity(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public AuthorizationManager<RequestAuthorizationContext> hasValidAccountId(Long userId) {
	

		
		return (authentication,context) -> {
			String currentUserName = (String) authentication.get().getPrincipal();

			Account account = accountRepository.findById(userId)
					.orElseThrow(() -> new SecurityException("Unauthorized attempt of editing account"));
			Account currentUserAccount = accountRepository.findAccountByUsername(currentUserName)
					.orElseThrow(() -> new SecurityException("Unexpected security exception"));
			

			boolean decision = currentUserAccount.getRole().equals(Role.ADMINISTRATOR) 
					|| account.getUsername().equals(currentUserName);
			
			return new AuthorizationDecision(decision);
		};
	}
}
