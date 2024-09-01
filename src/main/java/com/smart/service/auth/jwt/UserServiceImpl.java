package com.smart.service.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.rep.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
private final UserRepository userRepository;


public UserDetailsService userDetailsService() {
	// TODO Auto-generated method stub
	return new UserDetailsService() {
		
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			// TODO Auto-generated method stub
			return userRepository.findFirstByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		}
	};
}

}
