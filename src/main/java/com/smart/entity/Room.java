package com.smart.entity;

import com.smart.dto.RoomDto;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Room")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String type;
	
	private Long price;
	
	private boolean available;

	public RoomDto getRoomDto() {
		// TODO Auto-generated method stub
		RoomDto rdto=new RoomDto();
		rdto.setAvailable(available);
		rdto.setName(name);
		rdto.setId(id);
		rdto.setName(name);
		rdto.setPrice(price);
		rdto.setType(type);
		
		return rdto;
		
	}
}
