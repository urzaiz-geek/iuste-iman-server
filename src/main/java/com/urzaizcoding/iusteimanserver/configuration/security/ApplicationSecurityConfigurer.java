package com.urzaizcoding.iusteimanserver.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Permission;
import com.urzaizcoding.iusteimanserver.domain.user.Role;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.service.AccountService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfigurer {

	private AuthenticationManager authenticationManager;

	private final AccountService accountService;

	private final AppUserAuthenticationSuccessHandler successHandler;

	private final AppConfigurer configurer;

	public ApplicationSecurityConfigurer(AccountService accountService,
			AppUserAuthenticationSuccessHandler successHandler, AppConfigurer configurer) {
		super();
		this.accountService = accountService;
		this.successHandler = successHandler;
		this.configurer = configurer;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

		authBuilder.userDetailsService(new AppUserDetailsService(accountService));
		authenticationManager = authBuilder.build();
		// disable csrf and frames

		http.csrf().disable();

		http.headers().frameOptions().disable();

		// disable sessions

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// set authentication rules

		http.authorizeHttpRequests().mvcMatchers(HttpMethod.GET, "/courses").permitAll();
		http.authorizeHttpRequests().mvcMatchers(HttpMethod.GET, "/accounts/refreshToken").permitAll();
		http.authorizeHttpRequests().mvcMatchers(HttpMethod.GET, "/courses/{id}").permitAll();
		http.authorizeHttpRequests().mvcMatchers(HttpMethod.POST, "/courses/{id}/registrations").permitAll();
		http.authorizeHttpRequests().mvcMatchers(HttpMethod.POST, "/students/{studentId}/photo").permitAll();
		
		http.authorizeHttpRequests().mvcMatchers("/courses/{id}/regitrations/*").hasRole(Role.PRE_STUDENT.name());
		http.authorizeHttpRequests().mvcMatchers("/courses/*").hasRole(Role.ADMINISTRATOR.name());
		http.authorizeHttpRequests().mvcMatchers(HttpMethod.GET, "/courses/{id}/folders")
				.hasAuthority(Permission.READ_FOLDERS.name());

		http.authorizeHttpRequests().mvcMatchers(HttpMethod.DELETE, "/accounts/{id}")
				.hasRole(Role.ADMINISTRATOR.name());
		http.authorizeHttpRequests(
				authorize -> authorize.mvcMatchers("/accounts/{id}/**").access(hasValidAccountId(accountService)));
		http.authorizeHttpRequests().mvcMatchers("/accounts/**").hasRole(Role.ADMINISTRATOR.name());

		http.authorizeHttpRequests().mvcMatchers(HttpMethod.DELETE, "/folders/{folderRegistrationNumber}")
				.hasRole(Role.ADMINISTRATOR.name());
		http.authorizeHttpRequests().mvcMatchers("/folders/**").hasAnyRole(Role.ADMINISTRATOR.name(),
				Role.MANAGER.name());

		http.authorizeHttpRequests().anyRequest().authenticated();

		
		http.addFilterBefore(new JwtAutorisationFilter(configurer), UsernamePasswordAuthenticationFilter.class);
		http.addFilter(new JWTAuthenticationFilter(authenticationManager, configurer));
		
		http.authenticationManager(authenticationManager);

		return http.build();
	}

	public static AuthorizationManager<RequestAuthorizationContext> hasValidAccountId(AccountService accountService) {

		return (authentication, context) -> {
			String currentUserName = ((AppUserDetails) authentication.get().getPrincipal()).getUsername();
			boolean decision;

			// get the request parameters
			Long userId = Long.parseLong(context.getVariables().get("id"));

			Account account = null;
			Account currentUserAccount;
			try {
				account = accountService.getSpecificAccount(userId);
				currentUserAccount = accountService.findUserByUsername(currentUserName)
						.orElseThrow(() -> new SecurityException("Unexpected security exception"));
			} catch (ResourceNotFoundException e) {
				return new AuthorizationDecision(false);
			}

			decision = currentUserAccount.getRole().equals(Role.ADMINISTRATOR)
					|| account.getUsername().equals(currentUserName);

			return new AuthorizationDecision(decision);
		};
	}

}

@Configuration
class ExtraSecurityConfiguration {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
