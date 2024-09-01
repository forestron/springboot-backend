package com.smart.service.auth;

import com.smart.dto.RoomResponseDto;

public interface RoomServiceC {
	  public RoomResponseDto  findRoomByAvailable(int pageNumber);
}
