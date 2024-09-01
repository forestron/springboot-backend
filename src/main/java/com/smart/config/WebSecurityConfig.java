package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smart.enums.UserRole;
import com.smart.service.auth.jwt.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	//private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserService userService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
	  http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request-> request.requestMatchers("/api/auth/**").permitAll().
         requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name()).
			  requestMatchers("/api/customer/**").hasAnyAuthority(UserRole.CUSTOMER.name()).
		  anyRequest().authenticated()).sessionManagement(manager-> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
  authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		 
	return http.build();
	}

	
//	@Bean
//	 BCryptPasswordEncoder bCryptPasswordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}
	@Bean
	 AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService.userDetailsService());
		authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		return authProvider;
	}
//	
	@Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
}
