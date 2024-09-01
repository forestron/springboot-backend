package com.smart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.service.auth.RoomServiceC;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {
	
  private final RoomServiceC roomServiceC;
  
  
  @GetMapping("/room/{pageNumber}")
  public ResponseEntity<?> getAllRoom(@PathVariable int pageNumber)
  {
		return ResponseEntity.ok(roomServiceC.findRoomByAvailable(pageNumber));
  } 
}
