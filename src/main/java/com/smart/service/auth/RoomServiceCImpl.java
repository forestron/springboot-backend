package com.smart.service.auth;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart.dto.RoomResponseDto;
import com.smart.entity.Room;
import com.smart.rep.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceCImpl implements RoomServiceC{
 
	private final RoomRepository roomRepository;


	@Override
	  public RoomResponseDto  findRoomByAvailable(int pageNumber) {
		//pagination useful for large datasets
			  Pageable pageable=PageRequest.of(pageNumber,2);
			  Page<Room> roomPage = roomRepository.findRoomByAvailable(true,pageable);

			  RoomResponseDto roomResponseDto = new RoomResponseDto();
			  roomResponseDto.setPageNumber(roomPage.getNumber());
			  roomResponseDto.setTotalPages(roomPage.getTotalPages());
			  roomResponseDto.setRoomDtoList(roomPage.getContent().stream().map(Room::getRoomDto).collect(Collectors.toList()));
			  
			return roomResponseDto;
		  }
	
	

}
