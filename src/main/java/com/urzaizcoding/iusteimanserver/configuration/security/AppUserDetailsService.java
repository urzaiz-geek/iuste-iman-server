package com.urzaizcoding.iusteimanserver.configuration.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.service.AccountService;

public class AppUserDetailsService implements UserDetailsService {
	private AccountService accountService;

	public AppUserDetailsService(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account account = accountService.findUserByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("unable to find user with username %s", username)));
		
		return new AppUserDetails(account);
	}

}
