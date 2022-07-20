package com.urzaizcoding.iusteimanserver.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
import com.urzaizcoding.iusteimanserver.configuration.security.AppUserDetails;
import com.urzaizcoding.iusteimanserver.configuration.security.JWTAuthenticationProvider;
import com.urzaizcoding.iusteimanserver.configuration.security.Token;
import com.urzaizcoding.iusteimanserver.domain.Person;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Notification;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.mappers.NotificationRepository;
import com.urzaizcoding.iusteimanserver.repository.AccountRepository;
import com.urzaizcoding.iusteimanserver.repository.EffectivePersonRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final EffectivePersonRepository effectivePersonRepository;
	private final PasswordEncoder passwordEncoder;
	private final NotificationRepository notificationRepository;
	private final AppConfigurer appConfigurer;

	public AccountServiceImpl(AccountRepository accountRepository, EffectivePersonRepository effectivePersonRepository,
			PasswordEncoder passwordEncoder, NotificationRepository notificationRepository,
			AppConfigurer appConfigurer) {
		super();
		this.accountRepository = accountRepository;
		this.effectivePersonRepository = effectivePersonRepository;
		this.passwordEncoder = passwordEncoder;
		this.notificationRepository = notificationRepository;
		this.appConfigurer = appConfigurer;
	}

	@Override
	public Optional<Account> findUserByUsername(String username) {
		return accountRepository.findAccountByUsername(username);
	}

	@Override
	public void deleteAccountById(Long accountId) throws ResourceNotFoundException {

		accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException(""));

		accountRepository.deleteById(accountId);

	}

	@Override
	public Page<Account> getAllAccounts(Integer page, Integer size) {

		Pageable pageRequest = PageRequest.of(page, size);

		return accountRepository.findAll(pageRequest);
	}

	@Override
	@Transactional
	public Account saveAccount(Account account, Long ownerId) {
		Account oldAccount = null;

		if (account.getId() != null) {
			oldAccount = accountRepository.findById(account.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
			oldAccount.setUsername(account.getUsername());
			oldAccount.setActive(account.isActive());
			oldAccount.setPassword(passwordEncoder.encode(account.getPassword()));

		} else {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
			account.setCreationDate(LocalDateTime.now(AppConfigurer.appTimeZoneId()));
			oldAccount = accountRepository.save(account);
		}

		if (ownerId != null) {
			Person person = effectivePersonRepository.findById(ownerId)
					.orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

			person.setAccount(oldAccount);
		}

		return oldAccount;
	}

	@Override
	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public Account getSpecificAccount(@NotNull Long id) {
		return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
	}

	@Override
	public Account updateAccountStatus(@NotNull Long id, boolean status) {
		Account account = getSpecificAccount(id);
		account.setActive(status);
		return accountRepository.save(account);
	}

	@Override
	public Notification getSpecificNotification(Long id, Long notifId) {
		Notification notification = getSpecificAccount(id).getNotifications().stream().filter(n -> n.getId() == notifId)
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("Ressource not found"));

		return notification;
	}

	@Override
	public Page<Notification> getAccountNotification(Long id, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Notification> notifications = notificationRepository.findAllByAccount(id, pageable);

		return notifications;
	}

	@Override
	public Token refreshToken(HttpServletRequest request) throws Exception {
		String refreshToken = JWTAuthenticationProvider.parseTokenFromRequest(request);

		if (refreshToken != null) {
			// get an instance of the provider

			JWTAuthenticationProvider provider = JWTAuthenticationProvider.getProvider(appConfigurer);

			String username = provider.getUsernameFromAccessToken(refreshToken);

			if (username != null) {
				UserDetails user = new AppUserDetails(accountRepository.findAccountByUsername(username)
						.orElseThrow(() -> new SecurityException("Unexpected security exception ")));
				
				String accessToken = provider.createAccessToken(user, request.getRequestURL().toString());
				
				return new Token(refreshToken,accessToken);
			}

		}

		return null;
	}

}
