package com.urzaizcoding.iusteimanserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Role;
import com.urzaizcoding.iusteimanserver.service.AccountService;

@SpringBootApplication
public class IusteimanServerApplication {

	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(IusteimanServerApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return (String... args) -> {
			// add admin to database
			try {
				Account admin = Account.builder().username("root").role(Role.ADMINISTRATOR).password("123").active(true)
						.build();

				Account manager = Account.builder().username("ndo").password("456").role(Role.MANAGER).active(true).build();

				Account student = Account.builder().username("sake").password("789").role(Role.STUDENT).active(true).build();

				admin = accountService.saveAccount(admin, null);
				accountService.saveAccount(manager, null);
				accountService.saveAccount(student, null);
			}catch(Exception e) {
				
			}
		};
	}

}