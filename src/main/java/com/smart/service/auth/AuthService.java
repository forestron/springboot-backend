package com.smart.service.auth;

import com.smart.dto.SignUpRequest;
import com.smart.dto.UserDto;

public interface AuthService {
	public UserDto createRequest(SignUpRequest signUpRequest);
}
