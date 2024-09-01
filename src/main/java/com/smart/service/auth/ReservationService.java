package com.smart.service.auth;

import com.smart.dto.ReservationResponseDto;

public interface ReservationService {
	public ReservationResponseDto  getallreservations( int pageNumber);
	public boolean ChangeStatus(Long id, String status);
}
