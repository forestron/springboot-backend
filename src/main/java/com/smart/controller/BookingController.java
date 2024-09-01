package com.smart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dto.ReservationDto;
import com.smart.rep.ReservationRepository;
import com.smart.service.auth.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {
	 
	private final BookingService bookingService;
	
	@PostMapping("/postroom")
	public ResponseEntity<?> postBooking(@RequestBody ReservationDto reservationDto)
	{
		 boolean rDto=bookingService.bookRoom(reservationDto);
		 
		 if(rDto==true)
		 {
			 return ResponseEntity.status(HttpStatus.OK).build();
		 }
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		 
	}
   @GetMapping("/getBookings/{userId}/{pageNumber}")
	public ResponseEntity<?> getAllBookingByUserId(@PathVariable Long userId,@PathVariable int pageNumber)
	{
		try {
			return ResponseEntity.ok(bookingService.getAllReservationByUserID(userId, pageNumber));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went Wrong");
		}
	}
}
