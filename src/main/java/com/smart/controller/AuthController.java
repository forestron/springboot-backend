package com.smart.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dto.AuthenticationRequest;
import com.smart.dto.AuthenticationResponse;
import com.smart.dto.SignUpRequest;
import com.smart.dto.UserDto;
import com.smart.entity.User;
import com.smart.rep.UserRepository;
import com.smart.service.auth.AuthService;
import com.smart.service.auth.jwt.UserService;
import com.smart.util.JwtUtil;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
private final AuthService authService;
private final AuthenticationManager authenticationManager;
private final JwtUtil jwtUtil;
private final UserRepository userRepository;
private final UserService userService;
@PostMapping("/signup")
  public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest)
  {
	  try {
		UserDto createduser=authService.createRequest(signUpRequest);
		return new ResponseEntity<>(createduser,HttpStatus.OK);
		
	} catch (EntityExistsException entityExistsException) {
		return new ResponseEntity<>("User Already Exists",HttpStatus.NOT_ACCEPTABLE);
	}
	  catch (Exception e) {
			return new ResponseEntity<>("User Not Created",HttpStatus.BAD_REQUEST);
	}
  }

@PostMapping("/login")
public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest)
{
	try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		
	} catch (BadCredentialsException e) {
		// TODO: handle exception
		throw new BadCredentialsException("Incorrect Username or Password");
	}
	
	final UserDetails userDetails= userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
	Optional<User> user=userRepository.findFirstByEmail(userDetails.getUsername());
	final String jwttoken=jwtUtil.generateToken(userDetails);
	AuthenticationResponse authenticationResponse= new AuthenticationResponse();
	if(user.isPresent())
	{
		authenticationResponse.setJwt(jwttoken);
		authenticationResponse.setUserId(user.get().getId());
		authenticationResponse.setUserRole(user.get().getUserRole()); 
	}
	System.out.println(authenticationResponse.getJwt());
	
	return authenticationResponse;
}
}
