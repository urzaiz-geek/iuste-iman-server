package com.urzaizcoding.iusteimanserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urzaizcoding.iusteimanserver.configuration.security.Token;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Notification;
import com.urzaizcoding.iusteimanserver.dto.AccountDTO;
import com.urzaizcoding.iusteimanserver.dto.AccountDTOIn;
import com.urzaizcoding.iusteimanserver.dto.NotificationDTO;
import com.urzaizcoding.iusteimanserver.mappers.MapStructMapper;
import com.urzaizcoding.iusteimanserver.service.AccountService;

@RestController
@RequestMapping(path = "/accounts", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AccountController {

	private static final String ERROR_MESSAGE = "error-message";

	private static final String APPLICATION_JSON = "application/json";

	private final AccountService accountService;

	private final MapStructMapper mapper;

	public AccountController(AccountService accountService, MapStructMapper mapper) {
		super();
		this.accountService = accountService;
		this.mapper = mapper;
	}

	@GetMapping
	public ResponseEntity<Page<AccountDTO>> getAllAccounts(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) throws Exception {

		return ResponseEntity
				.ok(accountService.getAllAccounts(page, size).map(account -> mapper.accountToAccountDTO(account)));

	}

	@GetMapping(path = { "{id}" })
	public ResponseEntity<AccountDTO> getSpecificAccount(@PathVariable @NotNull Long id) throws Exception {
		return ResponseEntity.ok(mapper.accountToAccountDTO(accountService.getSpecificAccount(id)));
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTOIn accountResource,
			UriComponentsBuilder uriComponentsBuilder) throws Exception {

		Account accountEntity = mapper.accountDTOInToAccount(accountResource);
		accountEntity = accountService.saveAccount(accountEntity, accountResource.getOwnerId());

		UriComponents uriComponents = uriComponentsBuilder.path("/accounts/{id}").buildAndExpand(accountEntity.getId());

		return ResponseEntity.status(HttpStatus.CREATED).header("location", uriComponents.toUriString())
				.body(mapper.accountToAccountDTO(accountEntity));

	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, path = "/{id}")
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody @Valid AccountDTOIn accountResource,
			@PathVariable @NotNull Long id) throws Exception {
		Account accountEntity = mapper.accountDTOInToAccount(accountResource);

		accountEntity = accountService.saveAccount(accountEntity, null);

		return ResponseEntity.ok(mapper.accountToAccountDTO(accountEntity));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable @NotNull Long id) throws Exception {

		accountService.deleteAccountById(id);

		return ResponseEntity.ok().build();
	}

	@PatchMapping(path = "/{id}/status", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AccountDTO> updateAccountStatus(@PathVariable @NotNull Long id,
			@RequestBody @NotNull AccountDTOStatus status) throws Exception {
		AccountDTO accountResource = mapper
				.accountToAccountDTO(accountService.updateAccountStatus(id, status.isActive()));
		return ResponseEntity.ok(accountResource);
	}

	@GetMapping(path = { "/{id}/notifications" })
	public ResponseEntity<Page<NotificationDTO>> getAccountNotifications(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @PathVariable Long id) throws Exception {

		Page<Notification> notifications = accountService.getAccountNotification(id,page,size);

		return ResponseEntity.ok(notifications.map(notification -> mapper.notificationToNotificationDTO(notification)));
	}
	
	@GetMapping(path = {"{id}/notifications/{notifId}"})
	public ResponseEntity<Notification> getSpecificNotification(@PathVariable Long id, @PathVariable Long notifId) throws Exception {
		
		Notification notification = accountService.getSpecificNotification(id,notifId);
		return ResponseEntity.ok(notification);
	}

	@GetMapping(path = "/refreshToken")
	public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		Token idToken = accountService.refreshToken(request);
		
		if(idToken != null) {
			response.setContentType(APPLICATION_JSON);
			new ObjectMapper().writeValue(response.getOutputStream(), idToken);
		}else {
			response.setHeader(ERROR_MESSAGE, "Security exception");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	
}
