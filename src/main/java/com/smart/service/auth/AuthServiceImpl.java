package com.smart.service.auth;

import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.dto.SignUpRequest;
import com.smart.dto.UserDto;
import com.smart.entity.User;
import com.smart.enums.UserRole;
import com.smart.rep.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  
  @PostConstruct
  public void createAdminAccount()
  {
	  Optional<User> adminaccount=userRepository.findByUserRole(UserRole.ADMIN);
	  if(adminaccount.isEmpty())
	  {
		  User user=new User();
		  user.setEmail("admin@test.com");
		  user.setName("Admin");
		  user.setUserRole(UserRole.ADMIN);
		  user.setPassword(new BCryptPasswordEncoder().encode("admin"));
		  userRepository.save(user);
		  System.out.println("Admin Account Created Successfully");
	  }
	  else {
		
		  System.out.println("Admin Account Already Exists");
	}
	  
  }
  
  public UserDto createRequest(SignUpRequest signUpRequest)
  {
//	  Optional<User> usercheck=userRepository.findFirstByEmail(signUpRequest.getEmail());
//	  if(usercheck.isPresent())
//	  {
//		  throw new EntityExistsException("user already present with the email:"+ signUpRequest.getEmail());
//	  }
System.out.println(signUpRequest.getEmail());

	  User user=new User();
	  user.setEmail(signUpRequest.getEmail());
	  user.setName(signUpRequest.getName());
	  user.setUserRole(UserRole.CUSTOMER);
	  user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
	  
	  User createduser=userRepository.save(user);
	  return createduser.getUserDto();
  }
}
