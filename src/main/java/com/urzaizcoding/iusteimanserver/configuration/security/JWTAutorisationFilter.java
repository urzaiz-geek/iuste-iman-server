package com.urzaizcoding.iusteimanserver.configuration.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.service.AccountService;

public class JWTAutorisationFilter extends OncePerRequestFilter {

	private static final List<WhiteListItem> WHITE_LIST = List.of(new WhiteListItem(HttpMethod.GET, "/login"),
			new WhiteListItem(HttpMethod.GET, "/courses"),
			new WhiteListItem(HttpMethod.POST, "/courses/*/registrations"),
			new WhiteListItem(HttpMethod.GET, "/accounts/refreshToken"),
			new WhiteListItem(HttpMethod.POST, "/students/*/photo"));

	private AppConfigurer configurer;

	private final AccountService accountService;

	public JWTAutorisationFilter(AppConfigurer configurer, AccountService accountService) {
		super();
		this.configurer = configurer;
		this.accountService = accountService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// allow whitelisted resources

		if (WHITE_LIST.stream().anyMatch(wi -> wi.check(request))) {
			filterChain.doFilter(request, response);
			return;
		}

		String jwtToken = JWTAuthenticationProvider.parseTokenFromRequest(request);
		if (jwtToken != null) {
			try {

				Authentication securityToken = JWTAuthenticationProvider.getProvider(configurer)
						.authenticateAccessToken(jwtToken);
				SecurityContextHolder.getContext().setAuthentication(securityToken);

				// authentication succeed

				Account userAccount = accountService.findUserByUsername(securityToken.getName())
						.orElseThrow(() -> new SecurityException("Unexpected security exception"));

				userAccount.setLastConnectionDate(LocalDateTime.now(AppConfigurer.appTimeZoneId()));
				accountService.saveAccount(userAccount, null);

				filterChain.doFilter(request, response);
			} catch (Exception e) {
				response.setHeader("error-message", e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}

		} else {
			filterChain.doFilter(request, response);
		}

	}

}
