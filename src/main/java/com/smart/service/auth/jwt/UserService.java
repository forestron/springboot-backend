package com.smart.service.auth.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
	public UserDetailsService userDetailsService();
}
