package com.smart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.service.auth.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class ReservationController {
  private final ReservationService reservationService;
  
  
  @GetMapping("/reservations/{pageNumber}")
  private ResponseEntity<?> getAllReservations(@PathVariable int pageNumber)
  {
	   try {
		return ResponseEntity.ok(reservationService.getallreservations(pageNumber));
	} catch (Exception e) {
		// TODO: handle exception
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
	}
  }
  
  @GetMapping("/reservations/{id}/{status}")
  public ResponseEntity<?> changeRoomStatus(@PathVariable Long id, @PathVariable String status)
  {
	  boolean change=reservationService.ChangeStatus(id, status);
	  if(change==true)
		  return ResponseEntity.status(HttpStatus.OK).body(null);
	  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation not found");  }
}
