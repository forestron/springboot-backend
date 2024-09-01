package com.smart.service.auth;

import com.smart.dto.ReservationDto;
import com.smart.dto.ReservationResponseDto;

public interface BookingService {
	public boolean bookRoom(ReservationDto reservationDto);
	public ReservationResponseDto getAllReservationByUserID(Long userId,int pageNumber);
}
