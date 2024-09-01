package com.smart.rep;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.User;
import com.smart.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {
       Optional<User> findFirstByEmail(String email);
       Optional<User> findByUserRole(UserRole userRole);
}
