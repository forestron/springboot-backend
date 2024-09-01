package com.smart.service.auth;

import java.util.List;

import com.smart.dto.RoomDto;
import com.smart.dto.RoomResponseDto;

public interface RoomService  {
     boolean postroom(RoomDto rdto);
     public List<RoomDto> getAllRooms();
     RoomDto updateRoom(Long id, RoomDto roomDto);
     public RoomResponseDto  getAllRoom(int pageNumber);
     public RoomDto getRoomByID(Long id);
     public void deleteRoom(Long id);
}
