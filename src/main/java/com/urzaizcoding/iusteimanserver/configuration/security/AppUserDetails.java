package com.urzaizcoding.iusteimanserver.configuration.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.urzaizcoding.iusteimanserver.domain.user.Account;

public class AppUserDetails implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7107025681344143762L;

	private final Account userAccount;

	public AppUserDetails(Account userAccount) {
		super();
		this.userAccount = userAccount;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = userAccount.getRole().getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.name()))
				.collect(Collectors.toCollection(ArrayList::new));
		authorities.add(new SimpleGrantedAuthority("ROLE_" + userAccount.getRole().name()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return userAccount.getPassword();
	}

	@Override
	public String getUsername() {
		return userAccount.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return userAccount.isActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return userAccount.isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return userAccount.isActive();
	}

	@Override
	public boolean isEnabled() {
		return userAccount.isActive();
	}

}
