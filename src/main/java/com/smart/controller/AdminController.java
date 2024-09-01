package com.smart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.smart.dto.RoomDto;
import com.smart.service.auth.RoomService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
   
	
	private final RoomService roomService;
	
	@PostMapping("/room")
	public ResponseEntity<?> createRoom(@RequestBody RoomDto roomDto)
	{
		boolean rdto=roomService.postroom(roomDto);
		if(rdto==true)
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(rdto);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/room")
    public ResponseEntity<List<RoomDto>> getAllRooms()
    {
		return ResponseEntity.status(HttpStatus.OK).body(roomService.getAllRooms());
    }
	
	@PutMapping("/room/{id}")
	public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto)
	{
		RoomDto rdto=roomService.updateRoom(id,roomDto);
		if(rdto==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(rdto);
	}
	
	@GetMapping("/room/{pageNumber}")
    public ResponseEntity<?> getAllRoom(@PathVariable int pageNumber)
    {
		return ResponseEntity.ok(roomService.getAllRoom(pageNumber));
    }
	@GetMapping("/getroom/{id}")
    public ResponseEntity<?> getRoomByID(@PathVariable Long id)
    {
		try {
			return ResponseEntity.ok(roomService.getRoomByID(id));
		} catch (EntityNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
		
    }
	
	@DeleteMapping("/getroom/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id)
    {try {
    	roomService.deleteRoom(id);
		return ResponseEntity.ok(null);
	} catch (EntityNotFoundException e) {
		// TODO: handle exception
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
    }
	
	
	
}
