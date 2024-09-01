package com.smart.service.auth;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart.dto.RoomDto;
import com.smart.dto.RoomResponseDto;
import com.smart.entity.Room;
import com.smart.rep.RoomRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
private final RoomRepository roomRepository;

@Override
public boolean postroom(RoomDto rdto) {
	// TODO Auto-generated method stub
	try {
		Room room=new Room();
		room.setAvailable(true);
		room.setName(rdto.getName());
		room.setPrice(rdto.getPrice());
		room.setType(rdto.getType());
		
	   roomRepository.save(room);
	   return true;
	
	}
	catch (Exception e) {
		// TODO: handle exception
		return false;
	}
}

@Override
public List<RoomDto> getAllRooms() {
	// TODO Auto-generated method stub
	List<Room> roomlist=roomRepository.findAll();
	
	return roomlist.stream().map(Room::getRoomDto).collect(Collectors.toList());
}

@Override
public RoomDto updateRoom(Long id, RoomDto roomDto) {
	
	Optional<Room> room=roomRepository.findById(id);
	if(room.isPresent())
	{
		Room r=room.get();
		r.setName(roomDto.getName());
		r.setPrice(roomDto.getPrice());
		r.setAvailable(roomDto.isAvailable());
		r.setType(roomDto.getType());
		
		return roomRepository.save(r).getRoomDto();
	}
	
	return null;
}

  public RoomResponseDto  getAllRoom(int pageNumber) {
//pagination useful for large datasets
	  Pageable pageable=PageRequest.of(pageNumber,2);
	  Page<Room> roomPage = roomRepository.findAll(pageable);

	  RoomResponseDto roomResponseDto = new RoomResponseDto();
	  roomResponseDto.setPageNumber(roomPage.getNumber());
	  roomResponseDto.setTotalPages(roomPage.getTotalPages());
	  roomResponseDto.setRoomDtoList(roomPage.getContent().stream().map(Room::getRoomDto).collect(Collectors.toList()));
	  
	return roomResponseDto;
  }

@Override
public RoomDto getRoomByID(Long id) {
	// TODO Auto-generated method stub
	Optional<Room> room=roomRepository.findById(id);
	if(room.isPresent())
	{
	 return room.get().getRoomDto();
	}
	else {
		throw new EntityNotFoundException("Room not found");
	}
}

@Override
public void deleteRoom(Long id) {
  Optional<Room> r=roomRepository.findById(id);
  if(r.isPresent())
  {
	  roomRepository.deleteById(id);
	  
  }
  else
  {
	  throw new EntityNotFoundException("Room not found");
  }
  
}
}

