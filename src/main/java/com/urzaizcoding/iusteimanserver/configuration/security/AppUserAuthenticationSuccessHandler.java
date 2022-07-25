package com.urzaizcoding.iusteimanserver.configuration.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.repository.AccountRepository;

@Component
public class AppUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final AccountRepository accountRepository;

	public AppUserAuthenticationSuccessHandler(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("on authentication success");
		String username = authentication.getName();
		System.out.println("username");
		Account userAccount = accountRepository.findAccountByUsername(username)
				.orElseThrow(() -> new SecurityException("Unexpected security exception"));

		userAccount.setLastConnectionDate(LocalDateTime.now(AppConfigurer.appTimeZoneId()));
		accountRepository.save(userAccount);
	}

}
