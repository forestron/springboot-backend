package com.smart.entity;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.smart.dto.ReservationDto;
import com.smart.enums.ReservationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Reservation {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private Long price;
	private ReservationStatus reservationStatus;
	
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name="room_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Room room;
	
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name="user_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	public ReservationDto getReservationDto()
	{
		ReservationDto rDto=new ReservationDto();
		rDto.setCheckInDate(checkInDate);
		rDto.setCheckOutDate(checkOutDate);
		rDto.setId(id);
		rDto.setPrice(price);
        rDto.setReservationStatus(reservationStatus);
        rDto.setUserId(user.getId());
        rDto.setRoomId(room.getId());
        rDto.setUsername(user.getUsername());
        rDto.setRoomName(room.getName());
        rDto.setRoomType(room.getType());
        
        return rDto;
	}
	
	
}
